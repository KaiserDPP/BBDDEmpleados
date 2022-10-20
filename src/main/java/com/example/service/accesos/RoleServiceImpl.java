package com.example.service.accesos;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.entity.accesos.Role;
import com.example.entity.accesos.User;
import com.example.repository.accesos.RoleRepository;
import com.example.repository.accesos.UserRepository;
import com.example.utils.RolesEnum;





@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	@Override
	public Role createRole(String nombreRole) {
		Role nuevoRoleGuardar = new Role();
		nuevoRoleGuardar.setName(nombreRole);
		return roleRepository.save(nuevoRoleGuardar);

	}
	
	//@Scheduled(cron = "0 */1 * * * *", zone = "Europe/Paris")   // every minute
	@Override
	public List<Role> buscarTodosRoles() {
		List<Role> roles = roleRepository.findAll(); 
		System.out.print(roles);
		return roles;
	}

	@Override
	public Role buscarUnRole(String nombreRole) {
		return roleRepository.findByName(nombreRole);
	}
	@Override
	public String updateUser(String nombreRole,long idUsuario) {
		
		
		//Busco rol que existe
		Role roleAMeter = roleRepository.findByName(nombreRole);
		//Busco un usuario que existe
		User usuarioGuardado = userRepository.findById(idUsuario).get();	
		
		User usuarioActualizado = new User();
		
	if (usuarioGuardado != null) {
		usuarioGuardado.addRole(roleAMeter); 
		
		usuarioActualizado= userRepository.save(usuarioGuardado);
		 }
	return "usuario actualizado";
}
	@Override
	public String deleteRole(long id) {
		Optional<Role> roleOptional = roleRepository.findById(id);
		if (roleOptional.isPresent()) {
			Role roleEliminar = roleOptional.get();
			roleRepository.delete(roleEliminar);
			return "rol eliminado";
		}
		return "rol no existe en la BBDD";
	}

	@Override
	public String updateRole(String nombreRole, long id) {
		Optional<Role> roleOptional = roleRepository.findById(id);
		if (roleOptional.isPresent()) {
			Role roleActualizar = roleOptional.get();
			roleActualizar.setName(nombreRole);
			roleRepository.save(roleActualizar);
			return "rol actualizado";
		}
		return "rol no existe en la BBDD";
	}

	@Override
	public String quitarRolAdmin(Long idUsuario) {
		// Buscamos al usario que queremos modificar y está guardado
		User usuarioGuardado = userRepository.findById(idUsuario).get();
		// Recogemos la lista de roles que ya tiene el tio
		Set<Role> rolesDeUsuario = usuarioGuardado.getRoles();
		Role roleAeliminar = roleRepository.findByName(RolesEnum.ADMIN.getNombreRole());
		rolesDeUsuario.remove(roleAeliminar);
		userRepository.save(usuarioGuardado);
		return "role ADMIN eliminado del usuario";
	}

	@Override
	public String quitarRolCustomer(Long idUsuario) {
		User usuarioGuardado = buscarUsuarioQueExiste(idUsuario);
		quitarRolDeUsuarioQueExiste(usuarioGuardado, RolesEnum.ADMIN);
		return "role CUSTOMER eliminado del usuario";
	}

	private User buscarUsuarioQueExiste(Long idUsuario) {
		User usuarioGuardado = buscarUsuarioQueExiste(idUsuario);
		quitarRolDeUsuarioQueExiste(usuarioGuardado, RolesEnum.CUSTOMER);
		return userRepository.findById(idUsuario).get();
	}

	private void quitarRolDeUsuarioQueExiste(User user, RolesEnum rolesEnum) {
		Set<Role> rolesDeUsuario = user.getRoles();
		Role roleAeliminar = roleRepository.findByName(rolesEnum.getNombreRole());
		rolesDeUsuario.remove(roleAeliminar);
		userRepository.save(user);
	}

	@Override
	public String actualizarUsuarioYQuitarRole(User user, int idUsuario, String nombreRole) {
		// Primero buscamos al usuario que queremos modificar y ya esta guardado
		User usuarioGuardado = userRepository.findById(new Long(idUsuario)).get();
		// Cambiamos las propiedades que queremos alterar
		usuarioGuardado.setName(user.getName());
		usuarioGuardado.setEmail(user.getEmail());
		// Recogemos la lista de roles que ya tiene el tio
		Set<Role> rolesDeMatias = usuarioGuardado.getRoles();
		// Buscamos en solitario el rol que queremos retirarle
		Role rolQueVamosAQuitar = roleRepository.findByName(nombreRole);
		// Sustraemos el rol de la lista de roles
		rolesDeMatias.remove(rolQueVamosAQuitar);
		// Guardamos el usuario que ya esta alterado para que se guarden los cambios
		userRepository.save(usuarioGuardado);
		return "Fenomenal";
	}
}
	/*
	 * //Crear Usuario
	 * 
	 * @Override public String crearUsuario(UserDTO userDto, String MiRole) { String
	 * message = ""; Optional<Role> role = null; Role roleGrabar = null;
	 * 
	 * if (MiRole.equals("ADMIN") || MiRole.equals("USER")) { role =
	 * roleRepository.findByName(MiRole); if(role.isPresent()) { roleGrabar =
	 * role.get(); }else { roleGrabar = new Role(); roleGrabar.setName(MiRole); }
	 * User user = new User(userDto.getUser(), userDto.getPassword(),
	 * userDto.getEmail(), Arrays.asList(roleGrabar));
	 * 
	 * userRepository.save(user);
	 * 
	 * message = "¡¡ Usuario Creado con Exito !!"; }else { message =
	 * "¡¡ El Usuario NO se ha Creado, Rol NO ACEPTADO !!"; }
	 * 
	 * return message; }
	 */
/*
	@Override
	public String actualizarUsuarioYCrearRole(UserDTO userdto, int idUsuario, String nombreRole) {

		// conversión DTO a Entity
		User userEntrada = new User();
		userEntrada.setName(userdto.getUser());
		userEntrada.setEmail(userdto.getEmail());
		userEntrada.setPassword(userdto.getPassword());
		// comprobar si usuario existe en las tablas
		User usuarioQueExiste = buscarUsuarioQueExiste(new Long(idUsuario));
		if (usuarioQueExiste != null) {
			// podemos modificar
			Role rolequeExiste = buscarUnRole(nombreRole);
			if (rolequeExiste != null) {
				// hay que crear un rol nuevo
				Role nuevoRole = new Role();
				nuevoRole.setName(nombreRole);
				usuarioQueExiste.getRoles().add(nuevoRole);
			} else {
				// añadirlo al set de roles que ya tiene el usuario
				usuarioQueExiste.getRoles().add(rolequeExiste);
			}
			// actualiza los valores del usuario
			usuarioQueExiste.setName(userEntrada.getName());
			usuarioQueExiste.setEmail(userEntrada.getEmail());
			userRepository.save(usuarioQueExiste);
			return "usuario actualizado";
		}
		// no existe, podemos modificar
		return "el usuario no existe, no se puede modificar";
	}
}
	*/