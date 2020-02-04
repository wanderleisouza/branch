package com.example.branch.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.example.branch.spatial.GeoDistance;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Branch {
	
	@Id
	private String id;
	private String name;
	private double lon;
	private double lat;

	@Transient
	@JsonInclude(Include.NON_DEFAULT)
	private double distance;
	
	public BigDecimal getDistance(double lonB, double latB) {
		var d = new BigDecimal(GeoDistance.vincentyDistance(this.lon, this.lat, lonB, latB));
		return d.setScale(0, RoundingMode.HALF_UP);
	}

}
