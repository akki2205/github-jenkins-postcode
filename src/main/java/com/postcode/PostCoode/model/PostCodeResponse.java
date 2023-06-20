package com.postcode.PostCoode.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PostCodeResponse {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String name;
	
	private String postcode;
	
	private int quality;
	
	private int eastings;
	
	private int northings;
	
	private String country;
	
	private String nhs_ha;
	
	private double longitude;
	
	private double latitude;

	
	
}
