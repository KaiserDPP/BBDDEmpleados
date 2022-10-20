package com.example.controller.accesos;

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

import com.example.entity.accesos.User;
import com.example.service.accesos.UserService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;

@Api(tags = "Post Controller U")
@Tag (name = "Post Controller U", description = "Crud Rest Apis for Post Resources")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	//Crear 1 USUARIO
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		return ResponseEntity.ok(userService.createUser(user));

	}
	
	//Buscar 1 USUARIO POR ID
	@GetMapping("/search")
	public ResponseEntity<List<User>> searchUsers(@RequestParam("query") String query) {
		return ResponseEntity.ok(userService.searchJQPL(query));
	}
	
	//Buscar Todos los Usuarios
	@GetMapping("/all")
	public ResponseEntity<List<User>> buscarTodos() {
		return ResponseEntity.ok(userService.buscarTodosUsers());
	}
	
	//Actualizar un usuario
	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody User user, @RequestParam("id") long id) {
		return ResponseEntity.ok(userService.updateUser(user, id));

	}
	//Eliminar un usuario
	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteUser(@RequestBody User user, @RequestParam("id") long id) {
		return ResponseEntity.ok(userService.deleteUser(user, id));

	}

}
