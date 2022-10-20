package com.example.controller.accesos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.accesos.Role;
import com.example.entity.accesos.User;
import com.example.service.accesos.RoleService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;

@Api(tags = "Post Controller R")
@Tag(name = "Post Controller R", description = "Crud Rest Apis for Post Resources")
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

	RoleService roleService;

	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	// Crear un rol
	@PostMapping("/create/{nombreRole}")
	public ResponseEntity<Role> createUser(@PathVariable String nombreRole) {
		return ResponseEntity.ok(roleService.createRole(nombreRole));
		// http://localhost:8080/api/v1/roles/create/Admin(Nombre_Rol)
	}

	// Mostrar todos los roles
	@GetMapping("/all")
	public ResponseEntity<List<Role>> buscarTodos() {
		return ResponseEntity.ok(roleService.buscarTodosRoles());
		// http://localhost:8080/api/v1/roles/all
	}

	// Buscar un role por nombre
	@GetMapping("/uno/{nombreRole}")
	public ResponseEntity<Role> buscarUno(@PathVariable String nombreRole) {
		return ResponseEntity.ok(roleService.buscarUnRole(nombreRole));
		// http:localhost:8080/api/v1/roles/uno/Customer(Nombre_Rol)
	}

	// Eliminar un role por id
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> buscarUno(@PathVariable Long id) {
		return ResponseEntity.ok(roleService.deleteRole(id));
		// http://localhost:8080/api/v1/roles/delete/3
	}

	// Actualizar un rol por id
	@PutMapping("/update/{nombreRole}/{id}")
	public ResponseEntity<String> buscarUno(@PathVariable String nombreRole, @PathVariable Long id) {
		return ResponseEntity.ok(roleService.updateRole(nombreRole, id));
		// http://localhost:8080/api/v1/roles/update/Client/4
		// En el main escribo los datos a cambiar
	}

	// A partir de un id añadir un rol
	@PutMapping("/traeUserYMeteRol/{idUsuario}/{nombreRole}")
	public ResponseEntity<String> updateUserAddRole(@RequestBody User user, @PathVariable("idUsuario") int idUsuario,
			@PathVariable("nombreRole") String nombreRole) {
		return ResponseEntity.ok(roleService.updateUser(nombreRole, idUsuario));

	}

	// Modificar usuario y eliminar el rol
	@PutMapping("/updateUserRemoveRole/{idUsuario}/{nombreRole}")
	public ResponseEntity<String> updateUserRemoveRole(@RequestBody User user, @PathVariable("idUsuario") int idUsuario,
			@PathVariable("nombreRole") String nombreRole) {
		return ResponseEntity.ok(roleService.actualizarUsuarioYQuitarRole(user, idUsuario, nombreRole));
		// http://localhost:8080/api/v1/roles/updateUserRemoveRole/1/Admin
		// En el main escribo los datos a cambiar
	}
}
