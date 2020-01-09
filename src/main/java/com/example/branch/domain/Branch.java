package com.example.branch.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.example.branch.spatial.GeoDistance;

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
	private double distance;
	
	public Double getDistance(double lonB, double latB) {
		return GeoDistance.vincentyDistance(this.lon, this.lat, lonB, latB);
	}

}
