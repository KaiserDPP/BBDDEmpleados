package com.example.controller.accesos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.accesos.UserDto;
import com.example.service.accesos.UserService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;

@Api(tags = "Post Controller U")
@Tag (name = "Post Controller U", description = "Crud Rest Apis for Post Resources")
@RestController
@RequestMapping("/api/v2/users")
public class UserController2 {

	UserService userService;

	@Autowired
	public UserController2(UserService userService) {
		this.userService = userService;
	}
	
	//Crear 1 USUARIO
	@PostMapping("/create")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
		return ResponseEntity.ok(userService.createUser2(user));

	}
	
	//Buscar 1 USUARIO POR ID
	@GetMapping("/search")
	public ResponseEntity<List<UserDto>> searchUsers(@RequestParam("query") String query) {
		return ResponseEntity.ok(userService.searchJQPL2(query));
	}
	
	//Buscar Todos los Usuarios
	@GetMapping("/all")
	public ResponseEntity<List<UserDto>> buscarTodos() {
		return ResponseEntity.ok(userService.buscarTodosUsers2());
		
	}
	
	//Actualizar un usuario
	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody UserDto user, @RequestParam("id") long id) {
		return ResponseEntity.ok(userService.updateUser2(user, id));

	}
	//Eliminar un usuario
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteUser(@RequestBody UserDto user, @RequestParam("id") long id) {
		return ResponseEntity.ok(userService.deleteUser2(user, id));

	}

}
