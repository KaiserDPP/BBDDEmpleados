
package com.example.controller.accesos;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.accesos.JwtAuthResponse;
import com.example.dto.accesos.LoginDto;
import com.example.facade.AuthFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Api(tags = "Auth Controller")
@Tag(name = "Auth Controller", description = "Auth controller exposes login and register REST APIs")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthFacade authFacade;

    @Autowired
    public AuthController(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @ApiOperation(value = "REST API to login an already existing user")
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        return authFacade.authenticateUser(loginDto);
    }
}

    /*@ApiOperation(value = "REST API to register a new user")
    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<SimpleResponse> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        return authFacade.registerUser(registerDto);
    }

*/
