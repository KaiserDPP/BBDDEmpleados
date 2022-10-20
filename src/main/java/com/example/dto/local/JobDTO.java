package com.example.dto.local;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JobDTO {

	@ApiModelProperty(value = "job_title - Nombre del Trabajo")
    private String job_title;
	@ApiModelProperty(value = "min_salary - Salario mínimo del Trabajo")
    private double min_salary;
	@ApiModelProperty(value = "max_salary - Salario máximo del Trabajo")
    private double max_salary;
    
    
}
