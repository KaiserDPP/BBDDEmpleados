package com.example.repository.local;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.local.Job;


@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {

	@Query(value = "SELECT j FROM Job j "
		     + "WHERE j.job_title =:nomTrabajo")
	Optional<Job> findByName(String nomTrabajo);
	
}
