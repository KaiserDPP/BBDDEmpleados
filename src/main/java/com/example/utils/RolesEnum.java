package com.example.utils;


public enum RolesEnum {

	ADMIN(Constants.ADMIN), CUSTOMER(Constants.CUSTOMER);

	private final String nombreRole;

	RolesEnum(String nombreRole) {
		this.nombreRole = nombreRole;

	}

	public String getNombreRole() {
		return nombreRole;
	}

}
