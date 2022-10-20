package com.example.service.local;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import com.example.dto.local.EmployeeDTO;
import com.example.entity.local.Employee;



public interface EmployeeService {

	Optional<Employee> findById(int id);
	
	List<Employee> findAll();
	
	String borrarEmpleado(int id) throws MessagingException;
	
	String crearEmpleado(EmployeeDTO employeeDto, String Trabajo, String Departamento) throws MessagingException;

	String modificarEmpleado(EmployeeDTO employeeDto, int id, String Trabajo, String Departamento) throws MessagingException;
	
	//String AutoInformeEmpleados(HttpServletResponse response) throws IOException;
	String AutoInformeEmpleados() throws IOException, MessagingException;
}
