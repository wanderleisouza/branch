package com.example.branch.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Branch {
	
	@Id
	@GeneratedValue
	private String id;
	private String name;
	private Double lon;	
	private Double lat;

}
