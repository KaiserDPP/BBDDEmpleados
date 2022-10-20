package com.example.service.local;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.local.Department;
import com.example.repository.local.DepartmentRepository;



@Transactional(transactionManager = "postgresTransactionManager")
@Service
public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentRepository departmentRepository;
	
	@Autowired
	public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Override
	public Optional<Department> findById(int id) {
		return departmentRepository.findById(id);
	}

	@Override
	public List<Department> findAll() {
		return departmentRepository.findAll();
	}

	@Override
	public Optional<Department> buscarUnDepartamento(String nomDepartamento) {
		return departmentRepository.findByName(nomDepartamento);
	}
}
