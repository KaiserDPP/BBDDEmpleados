package com.example.service.local;

import java.util.List;
import java.util.Optional;

import com.example.dto.local.JobDTO;
import com.example.entity.local.Job;



public interface JobService {

	Optional<Job> findById(int id);
	
	List<Job> findAll();
	
	String borrarJob(int id);

	String crearTrabajo(JobDTO jobDto);
	
	Optional<Job> buscarUnTrabajo(String nomTrabajo);

}
