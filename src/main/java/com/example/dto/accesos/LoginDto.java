package com.example.dto.accesos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

	@ApiModelProperty(value = "Username or Email of the user")
	@NotEmpty(message = "Username or email should not be null or empty")
	@Size(min = 5, max = 120, message = "Username or email should be between 5 and 120 characters")
	private String usernameOrEmail;

	@ApiModelProperty(value = "Password of the user")
	@NotEmpty(message = "Password should not be null or empty")
	@Size(min = 2, max = 10, message = "Password should be between 2 and 10 characters")
	private String password;
}

