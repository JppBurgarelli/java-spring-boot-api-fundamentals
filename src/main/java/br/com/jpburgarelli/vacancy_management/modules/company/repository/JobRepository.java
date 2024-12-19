package br.com.jpburgarelli.vacancy_management.modules.company.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jpburgarelli.vacancy_management.modules.company.entities.JobEntity;


public interface JobRepository extends JpaRepository<JobEntity, UUID> {} 


