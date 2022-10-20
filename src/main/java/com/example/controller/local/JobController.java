package com.example.controller.local;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.local.Job;
import com.example.service.local.JobService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Api(tags="Job Controller") //anotacion para documentar con Swagger
@Tag(name = "Job Controller", description = "CRUD de Trabajos de APP MyPeople")
@RestController
@RequestMapping("/api/v1/trabajos")
public class JobController {

	private JobService jobService;

	@Autowired
	public JobController(JobService jobService) {
		this.jobService = jobService;
	}
	
	//Listar Trabajos por ID
	@ApiOperation(value="Ver Datos de un Trabajo buscando por ID") //anotacion para documentar con Swagger
	@GetMapping("/verTrabajo/{id}")
	public ResponseEntity<Optional<Job>> buscaTrabajoss(@PathVariable("id") int id) {
		return ResponseEntity.ok(jobService.findById(id));
	}
	
	//Listar todas los Trabajos
	@ApiOperation(value="Listar Todas los Trabajos") //anotacion para documentar con Swagger
	@GetMapping("/listaTrabajos")
	public ResponseEntity<List<Job>> listarLocalidades() {
		return ResponseEntity.ok(jobService.findAll());
	}
	
	//Borrar Trabajos por ID
	@ApiOperation(value="Borra el Trabajo buscado por ID") //anotacion para documentar con Swagger
	@DeleteMapping("/borrarTrabajo")
	public ResponseEntity<String> borrarTrabajo(@RequestParam("id") int id) {
		
		//ejecuta el borrado del trabajo
		return ResponseEntity.ok(jobService.borrarJob(id));
	}
}
