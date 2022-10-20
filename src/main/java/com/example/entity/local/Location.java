package com.example.entity.local;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table( name = "locations", schema = "local")
public class Location {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int location_id; 				//SERIAL PRIMARY KEY,
	
	@Column(name = "street_address", length = 40, nullable = true)
    private String street_address;				//CHARACTER VARYING(40),
    
	@Column(name = "postal_code", length = 12, nullable = true)
	private String postal_code  ;  				//CHARACTER VARYING(12),
	
	@Column(name = "city", length = 30, nullable = false)
	private String city;          				//CHARACTER VARYING(30) NOT NULL,
	
	@Column(name = "state_province", length = 25, nullable = true)
	private String state_province; 				//CHARACTER VARYING(25),
	
}

/*CREATE TABLE locations
(
    location_id    SERIAL PRIMARY KEY,
    street_address CHARACTER VARYING(40),
    postal_code    CHARACTER VARYING(12),
    city           CHARACTER VARYING(30) NOT NULL,
    state_province CHARACTER VARYING(25),
    country_id     CHARACTER(2)          NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countries (country_id) ON UPDATE CASCADE ON DELETE CASCADE
);
*/