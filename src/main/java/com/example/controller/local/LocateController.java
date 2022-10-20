package com.example.controller.local;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.local.Location;
import com.example.service.local.LocationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Api(tags="Locate Controller") //anotacion para documentar con Swagger
@Tag(name = "Locate Controller", description = "CRUD de Localidades de APP MyPeople")
@RestController
@RequestMapping("/api/v1/localidades")
public class LocateController {

	private LocationService locationService;

	@Autowired
	public LocateController(LocationService locationService) {
		this.locationService = locationService;
	}
	
	//Listar Departamento por ID
	@ApiOperation(value="Ver Datos de una Localidad buscando por ID") //anotacion para documentar con Swagger
	@GetMapping("/verLocalidad/{id}")
	public ResponseEntity<Optional<Location>> buscaLocalidades(@PathVariable("id") int id) {
		return ResponseEntity.ok(locationService.findById(id));
	}
	
	
	
	//Listar todas las Localidades
	@ApiOperation(value="Listar Todas las Localidades") //anotacion para documentar con Swagger
	@GetMapping("/listaLocalidades")
	public ResponseEntity<List<Location>> listarLocalidades() {
		return ResponseEntity.ok(locationService.findAll());
	}
	
	
}
