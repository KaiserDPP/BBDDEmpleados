package com.example.controller.local;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.local.EmployeeDTO;
import com.example.entity.local.Employee;
import com.example.service.email.EmailService;
import com.example.service.local.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Api(tags="Employee Controller") //anotacion para documentar con Swagger
@Tag(name = "Employee Controller", description = "CRUD de Empleados de APP MyPeople")
@RestController
@RequestMapping("/api/v1/empleados")
public class EmployeeController {

	private EmployeeService employeeService;
	private EmailService emailService;

	@Autowired
	public EmployeeController(EmployeeService employeeService, EmailService emailService) {
		this.employeeService = employeeService;
		this.emailService = emailService;
	}
	
	//Listar Empleados por ID
	@ApiOperation(value="Ver Datos de un Empleado buscado por ID") //anotacion para documentar con Swagger
	@GetMapping("/verEmpleado/{id}")
	public ResponseEntity<Optional<Employee>> buscaEmpleado(@PathVariable("id") int id) {
		return ResponseEntity.ok(employeeService.findById(id));
	}
	
	//Informe de Empleados - AUTOMATICO - Diario 09:00 --> cron="0 9 * * 1"
	//@Scheduled(cron = "*/1 * * * * ", zone = "Europe/Paris")  // AUTOMATIZACION DE PROCESOS every minute 
	//PARAMETROS DE cron = <seconds 0-59> <minute(0-59)> <hour(0-23)> <day-of-month(1-31)> <month(1-12)> <day-of-week(0-6 || SUN-SAT)> <command>
	// url documentation = https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions
	//SITIO MUY UTIL PARA GENERAR PARAMETROS CRON ( http://crontab.cronhub.io/ )
	//ojo necesita la anotacion @EnableScheduling después de @SpringBootApplication
	
	@ApiOperation(value="Informe de Todos los Empleados, AutoEnvío diario 09:00") //anotacion para documentar con Swagger
	@Scheduled(cron = "0 43 9 * * 1", zone = "Europe/Paris")
	@GetMapping("/AutoInformeEmpleados")
	public ResponseEntity<String> AutoInformeEmpleados() throws IOException, MessagingException {	
		
		return ResponseEntity.ok(employeeService.AutoInformeEmpleados());
	}
	
	
	
	//Listar todos los Empleados
	@ApiOperation(value="Listar Todos los Empleados") //anotacion para documentar con Swagger
	@GetMapping("/listaEmpleados")
	public ResponseEntity<List<Employee>> listarEmpleados() {
		return ResponseEntity.ok(employeeService.findAll());
	}
	
	//Creamos un Empleado, 
	//devolvemos el Usuario desde el servicio
	@ApiOperation(value="Crear Empleados") //anotacion para documentar con Swagger
	@PostMapping("/crearEmpleado/{Qtrabajo}/{Qdepartamento}")
	public ResponseEntity<String> crearEmpleado(@RequestBody EmployeeDTO employeeDto, 
								@PathVariable("Qtrabajo") String QueTrabajo, 
								@PathVariable("Qdepartamento") String QueDepartamento) throws MessagingException {

			return ResponseEntity.ok(employeeService.crearEmpleado(employeeDto, QueTrabajo, QueDepartamento));
	}
	
	
	//Modificamos un Empleado, 
	//devolvemos el Usuario desde el servicio
	@ApiOperation(value="Modificar Empleados") //anotacion para documentar con Swagger
	@PutMapping("/modificaEmpleado/{idEmpleado}/{Qtrabajo}/{Qdepartamento}")
	public ResponseEntity<String> modificaEmpleado(@RequestBody EmployeeDTO employeeDto,
								@PathVariable("idEmpleado") int id,
								@PathVariable("Qtrabajo") String QueTrabajo, 
								@PathVariable("Qdepartamento") String QueDepartamento) throws MessagingException {
		
		return ResponseEntity.ok(employeeService.modificarEmpleado(employeeDto, id, QueTrabajo, QueDepartamento));
	}
	
	//Eliminar un Empleado, 
	//devolvemos el Usuario desde el servicio
	@ApiOperation(value="Eliminar Empleados") //anotacion para documentar con Swagger
	@DeleteMapping("/borraEmpleado/{idEmpleado}")
	public ResponseEntity<String> borrarEmpleado(@PathVariable("idEmpleado") int id) throws MessagingException {

		return ResponseEntity.ok(employeeService.borrarEmpleado(id));
	}
	
}
