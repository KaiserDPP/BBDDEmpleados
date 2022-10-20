
package com.example.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.dto.accesos.JwtAuthResponse;
import com.example.dto.accesos.LoginDto;
import com.example.token.JWTTokenProvider;

@Service
public class AuthFacadeImpl implements  AuthFacade{

	private JWTTokenProvider jwtTokenProvider;
	
	 @Autowired
	 private AuthenticationManager authenticationManager;
	
	@Autowired
	public AuthFacadeImpl(JWTTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
		
		this.jwtTokenProvider = tokenProvider;
		this.authenticationManager = authenticationManager;
	}
	
	@Override
	public ResponseEntity<JwtAuthResponse> authenticateUser(LoginDto loginDto) {
	    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	            loginDto.getUsernameOrEmail(), loginDto.getPassword()));
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	    String token = jwtTokenProvider.generateToken(authentication);
	    return new ResponseEntity<>(new JwtAuthResponse(token), HttpStatus.OK);
	}
}
