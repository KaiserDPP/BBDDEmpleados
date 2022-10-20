package com.example.dto.local;

import java.util.Date;

import com.example.entity.local.Department;
import com.example.entity.local.Job;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDTO {

	@ApiModelProperty(value = "first_name - Nombre del Empleado")
    private String first_name;
	@ApiModelProperty(value = "last_name - Apellido del Empleado")
    private String last_name;
	@ApiModelProperty(value = "email - Email del Empleado")
    private String email;
	@ApiModelProperty(value = "phone_number - Teléfono del Empleado")
    private String phone_number;
	@ApiModelProperty(value = "hire_date - Fecha de Contratación del Empleado")
    private Date hire_date;
	@ApiModelProperty(value = "salary - Salario del Empleado")
    private double salary;
    
	@ApiModelProperty(value = "jobEmployee - Trabajo o tarea del Empleado")
    private Job jobEmployee;
	
	@ApiModelProperty(value = "depEmployee - Departamento del Empleado")
    private Department depEmployee;
}
