
package com.example.facade;

import org.springframework.http.ResponseEntity;

import com.example.dto.accesos.JwtAuthResponse;
import com.example.dto.accesos.LoginDto;



public interface AuthFacade {
	
	ResponseEntity<JwtAuthResponse> authenticateUser(LoginDto loginDto);

}
