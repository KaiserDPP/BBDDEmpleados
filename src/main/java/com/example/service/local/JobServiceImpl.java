package com.example.service.local;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dto.local.JobDTO;
import com.example.entity.local.Job;
import com.example.repository.local.JobRepository;
import com.example.utils.Constants;



@Transactional(transactionManager = "postgresTransactionManager")
@Service
public class JobServiceImpl implements JobService {
	
	private JobRepository jobRepository;
	
	@Autowired
	public JobServiceImpl(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	//Buscar uno por ID
	@Override
	public Optional<Job> findById(int id) {
		return jobRepository.findById(id);
	}

	//Listar Todos
	@Override
	public List<Job> findAll() {
		return jobRepository.findAll();
	}

	//Borrar UNO POR ID
	@Override
	public String borrarJob(int id) {
	
		if(!jobRepository.existsById(id)) {
			Constants.message = "¡¡ El Trabajo que solicitas NO Existe !!";
		}else {
			//Buscamos el TRABAJO que existe para borrarlo
			Job existingJob = jobRepository.findById(id).get();
			
			jobRepository.delete(existingJob);
			Constants.message = "¡¡ El Trabajo Ha sido BORRADO !!";
		}
		return Constants.message;
	}
	
	//Crear
	@Override
	public String crearTrabajo(JobDTO jobDto) {
			
			Job job = new Job();
			job.setJob_title(jobDto.getJob_title());
			job.setMin_salary(jobDto.getMin_salary());
			job.setMax_salary(jobDto.getMax_salary());
			
			jobRepository.save(job);
		 
			return Constants.message = "¡¡ Trabajo Creado con Exito !!";

	}

	@Override
	public Optional<Job> buscarUnTrabajo(String nomTrabajo) {
		return jobRepository.findByName(nomTrabajo);
	}

}
