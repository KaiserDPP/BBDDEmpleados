package com.example.repository.local;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.local.Employee;


public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	

}
