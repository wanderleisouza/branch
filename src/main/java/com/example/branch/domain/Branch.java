package com.example.branch.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @RequiredArgsConstructor
public class Branch {
	
	@Id
	@GeneratedValue
	private String id;
	private String name;
	private Double lon;	
	private Double lat;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private Double distance;

}
