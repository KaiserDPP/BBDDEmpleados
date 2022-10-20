package com.example.controller.local;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.local.Department;
import com.example.service.local.DepartmentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Api(tags="Department Controller") //anotacion para documentar con Swagger
@Tag(name = "Department Controller", description = "CRUD de Departmentamentos de APP MyPeople")
@RestController
@RequestMapping("/api/v1/departamentos")
public class DepartmentController {

	private DepartmentService departmentService;

	@Autowired
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	//Listar Departamento por ID
	@ApiOperation(value="Ver Datos de un Departamento buscado por ID") //anotacion para documentar con Swagger
	@GetMapping("/verDepartamento/{id}")
	public ResponseEntity<Optional<Department>> buscaDepartemanto(@PathVariable("id") int id) {
		return ResponseEntity.ok(departmentService.findById(id));
	}
	
	
	
	//Listar todos los Departamentos
	@ApiOperation(value="Listar Todos los Departamentos") //anotacion para documentar con Swagger
	@GetMapping("/listaDepartamentos")
	public ResponseEntity<List<Department>> listarDepartamentos() {
		return ResponseEntity.ok(departmentService.findAll());
	}
	
	
	
}
