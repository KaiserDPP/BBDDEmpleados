package com.example.dto.accesos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "User model information")
public class UserDto {
	
	//@ApiModelProperty(value = "UserDto id")
	//private Long id;
	@ApiModelProperty(value = "name - Nombre del DTO Usuario")
	private String name;
	
	@ApiModelProperty(value = "email - Email del DTO Usuario")
	private String email;
	
	@ApiModelProperty(value = "password - Password del DTO Usuario")
	private String password;
	
	@ApiModelProperty(value = "active - Activo (Si o No) del DTO Usuario")
	private boolean active;
	
}

