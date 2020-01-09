package com.example.branch.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.example.branch.spatial.GeoDistance;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @RequiredArgsConstructor @ToString
public class Branch {

	@Id
	@GeneratedValue
	private String id;
	private String name;
	private double lon;
	private double lat;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private double distance;

	public double getDistance(double lonB, double latB) {
		return GeoDistance.vincentyDistance(this.lon, this.lat, lonB, latB);
	}

}
