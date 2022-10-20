package com.example.entity.local;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "employees", schema = "local")
public class Employee {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int employee_id;   				//SERIAL PRIMARY KEY,
	
	@Column(name = "first_name", length = 20, nullable = true)
    private String first_name;    				//CHARACTER VARYING(20),
    
	@Column(name = "last_name", length = 25, nullable = false)
    private String last_name;     				//CHARACTER VARYING(25)  NOT NULL,
    
	@Column(name = "email", length = 100, nullable = false)
    private String email;         				//CHARACTER VARYING(100) NOT NULL,
    
	@Column(name = "phone_number", length = 20, nullable = true)
    private String phone_number;  				//CHARACTER VARYING(20),
    
	@Column(name = "hire_date", nullable = false)
    private Date hire_date;     				//DATE    NOT NULL,
    
	@Column(name = "salary", nullable = false)
    private double salary;        				//NUMERIC(8, 2)          NOT NULL,
    
	
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "job_id", referencedColumnName = "job_id")
    private Job jobEmployee;
	
	@OneToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department depEmployee;
	
}


/*
 * CREATE TABLE employees
(
    employee_id   SERIAL PRIMARY KEY,
    first_name    CHARACTER VARYING(20),
    last_name     CHARACTER VARYING(25)  NOT NULL,
    email         CHARACTER VARYING(100) NOT NULL,
    phone_number  CHARACTER VARYING(20),
    hire_date     DATE                   NOT NULL,
    job_id        INTEGER                NOT NULL,
    salary        NUMERIC(8, 2)          NOT NULL,
    department_id INTEGER,
    FOREIGN KEY (job_id) REFERENCES jobs (job_id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (department_id) REFERENCES departments (department_id) ON UPDATE CASCADE ON DELETE CASCADE
);
 */