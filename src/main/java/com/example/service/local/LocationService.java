package com.example.service.local;

import java.util.List;
import java.util.Optional;

import com.example.entity.local.Location;


public interface LocationService {

	Optional<Location> findById(int id);
	
	List<Location> findAll();

}
