package com.example.entity.local;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table( name = "jobs", schema = "local")
public class Job {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int job_id;     				//SERIAL PRIMARY KEY,
	
	@Column(name = "job_title", length = 35, nullable = false)
    private String job_title;  				//CHARACTER VARYING(35) NOT NULL,
	
	@Column(name = "min_salary", nullable = true)
    private double min_salary; 				//NUMERIC(8, 2),
	 
	@Column(name = "max_salary", nullable = true)
    private double max_salary; 				//NUMERIC(8, 2)
}

/*CREATE TABLE jobs
(
    job_id     SERIAL PRIMARY KEY,
    job_title  CHARACTER VARYING(35) NOT NULL,
    min_salary NUMERIC(8, 2),
    max_salary NUMERIC(8, 2)
);
*/