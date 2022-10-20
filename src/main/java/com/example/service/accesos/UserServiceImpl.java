package com.example.service.accesos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.dto.accesos.UserDto;
import com.example.entity.accesos.Role;
import com.example.entity.accesos.User;
import com.example.repository.accesos.RoleRepository;
import com.example.repository.accesos.UserRepository;



@Service
public class UserServiceImpl implements UserService {

	UserRepository userRepository;

	RoleRepository roleRepository;

	ModelMapper modelMapper;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.modelMapper = modelMapper;
	}

	// MÉTODOS SIN DTO

	// Crear un usuario
	@Override
	public User createUser(User user) {
		Role roleUsuario = roleRepository.findByName("ROLE_USER");
		user.addRole(roleUsuario);
		return userRepository.save(user);
	}

	// Listar 1 Usuario
	@Override
	public List<User> searchJQPL(String query) {
		return userRepository.searchUser(query);
	}

	// Listar todos los usuarios

	@Override
	public List<User> buscarTodosUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}

	// Actualizar un usuario
	@Override
	public String updateUser(User user, long id) {
		String mensaje = " ";

		User existingUserId = userRepository.findById(id).get();

		if (existingUserId != null) {
			existingUserId.setName(user.getName());
			existingUserId.setEmail(user.getEmail());
			existingUserId.setPassword(user.getPassword());
			existingUserId.setUsername(user.getUsername());
			existingUserId.setActive(user.isActive());

			userRepository.save(existingUserId);
			mensaje = "usuario actualizado";
		} else {
			mensaje = "usuario no existe en base de datos";
		}
		return mensaje;
	}

	// Eliminar un usuario

	@Override
	public String deleteUser(User user, long id) {
		String mensaje = " ";

		User existingUserId = userRepository.findById(id).get();

		if (existingUserId != null) {
			user.setId(id);

			userRepository.delete(existingUserId);
			mensaje = "usuario eliminado";
		} else {
			mensaje = "usuario no eliminado";
		}
		return mensaje;
	}

	// EXTRACION DE LISTA DE USUARIOS AUTOMÁTICA
	//@Scheduled(cron = "*/10 * * * * *", zone = "Europe/Paris")
	@Override
	public void Imprimir_informacion() {

		List<User> usuarios = new ArrayList<>();
		usuarios = userRepository.findAll();

		for (User listaUsuario : usuarios) {

			System.out.println(listaUsuario.getName());
		}
	}

	/////////////////////

	// Métodos con DTO

	// Listar 1 Usuario

	@Override
	public List<UserDto> searchJQPL2(String query)

	{
		List<UserDto> listaDto = new ArrayList<UserDto>();
		List<User> lista = userRepository.searchUser(query);
		for (User userlista : lista) {
			listaDto.add(maptoDto(userlista));
		}
		return listaDto;
	}

	// Listar todos los usuarios

	@Override
	public List<UserDto> buscarTodosUsers2() {
		List<UserDto> listaDto = new ArrayList<UserDto>();

		List<User> users = userRepository.findAll();
		for (User userlista : users) {
			listaDto.add(maptoDto(userlista));
		}
		return listaDto;
	}

	// Crear 1 usuario

	@Override
	public UserDto createUser2(UserDto user) {
		// creamos el objeto vario para devolver que pide este metodo
		UserDto userDto = new UserDto();
		// convertimos lo que llega (dto) en lo que se graba (entity)
		User usuarioConvertido = maptoEntity(user);
		// hacemos la operacion con la entidad ya convertida
		User usuarioSalvado = userRepository.save(usuarioConvertido);
		// convertimos lo que hemos salvado (user) en lo que se devuelve (dto)
		userDto = maptoDto(usuarioSalvado);
		// devolvemos el dto que al principio estaba vacio
		return userDto;
	}

	// Eliminar 1 usuario

	@Override
	public String deleteUser2(UserDto user, long id) {
		UserDto userDto = new UserDto();
		User usuarioConvertido = maptoEntity(user);
		userRepository.delete(usuarioConvertido);
		return "Usuario eliminado";

	}

	// Actualizar 1 usuario

	@Override
	public String updateUser2(UserDto user, long id) {
		UserDto userDto = new UserDto();
		User usuarioConvertido = maptoEntity(user);
		String mensaje = "";
		User existingUserId = userRepository.findById(id).get();
		// Product existingProduct = productRepository.findBySku(sku).get();

		if (existingUserId != null) {
			// user.setId(id);

			existingUserId.setName(usuarioConvertido.getName());
			existingUserId.setEmail(usuarioConvertido.getEmail());
			existingUserId.setPassword(usuarioConvertido.getPassword());
			existingUserId.setUsername(usuarioConvertido.getUsername());
			existingUserId.setActive(usuarioConvertido.isActive());

			userRepository.save(existingUserId);
			mensaje = "usuario actualizado";
		} else {
			mensaje = "usuario no existe en base de datos";
		}
		return mensaje;

	}

	private UserDto maptoDto(User user) {
		return modelMapper.map(user, UserDto.class);
	}

	private User maptoEntity(UserDto dto) {
		return modelMapper.map(dto, User.class);
	}
}
/*
 * private EmailBody buildEmailBody(String email) {
 * //LOGGER.info("Local environment: " +
 * environment.getProperty("local.environment"));
 * 
 * return new EmailBody(email, "Operation completed successfully for user",
 * "Archive in bulk"); }
 */