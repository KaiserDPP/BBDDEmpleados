package com.example.service.accesos;

import java.util.List;
import java.util.Optional;

import com.example.dto.accesos.UserDto;
import com.example.entity.accesos.User;





public interface UserService {

	List<User> searchJQPL(String query);
	
	List<User> buscarTodosUsers();
	
	User createUser(User user);
	
	String deleteUser(User user, long id);

	String updateUser(User user, long id);
	
	/////////
	
	List<UserDto> searchJQPL2(String query);
	
	List<UserDto> buscarTodosUsers2();
	
	UserDto createUser2(UserDto user);
	
	String deleteUser2(UserDto user, long id);

	String updateUser2(UserDto user, long id);

	void Imprimir_informacion();
	

}
