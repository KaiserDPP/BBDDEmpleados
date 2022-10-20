package com.example.repository.accesos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.accesos.User;





public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsernameOrEmail(String username, String email);

	@Query("SELECT u FROM User u WHERE " +
			"u.id LIKE CONCAT('%',:query,'%')")
	List<User> searchUser(String query);
	
	Optional<User> findById(Long id);
	
	

}
