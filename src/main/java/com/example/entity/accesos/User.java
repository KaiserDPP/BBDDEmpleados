package com.example.entity.accesos;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ManyToAny;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Comment model information U")
@Data
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "email", "username" }) })

		
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty (value = "id")
	private Long id;
	@ApiModelProperty (value = "name")
	private String name;
	@ApiModelProperty (value = "email")
	private String email;
	@ApiModelProperty (value = "password")
	private String password;
	@ApiModelProperty (value = "username")
	private String username;
	@ApiModelProperty (value = "active")
	private boolean active;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable (
			name = "user_roles",
			joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id")
			)
	private Set<Role> roles = new HashSet<>();

	public void addRole(Role roleUser) {
		this.getRoles().add(roleUser);
	}

	
}
