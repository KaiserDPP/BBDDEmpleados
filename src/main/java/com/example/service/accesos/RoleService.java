package com.example.service.accesos;

import java.util.List;
import java.util.Optional;

import com.example.entity.accesos.Role;
import com.example.entity.accesos.User;





public interface RoleService {
	
	Role createRole(String  nombreRole);

	List<Role> buscarTodosRoles();
	
	Role buscarUnRole(String  nombreRole);
	
	String deleteRole(long id);

	String updateRole(String nombreRole, long id);
	
	String quitarRolAdmin(Long idUsuario);
	
	String quitarRolCustomer(Long idUsuario);
	
	String actualizarUsuarioYQuitarRole(User user, int idUsuario, String nombreRole);
	
	//String actualizarUsuarioYCrearRole(UserDTO userdto, int idUsuario, String nombreRole);

	
	public String updateUser(String nombreRole,long idUsuario);
	


	
	

	


}
