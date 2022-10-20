package com.example.repository.local;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.local.Department;


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	@Query(value = "SELECT d FROM Department d "
		     + "WHERE d.department_name =:nomDepartamento")
	Optional<Department> findByName(String nomDepartamento);
}
