package com.example.service.local;

import java.util.List;
import java.util.Optional;

import com.example.entity.local.Department;


public interface DepartmentService {
	
	Optional<Department> findById(int id);
	
	List<Department> findAll();
	
	Optional<Department> buscarUnDepartamento(String nomDepartamento);

}
