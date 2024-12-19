package br.com.jpburgarelli.vacancy_management.modules.candidate.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jpburgarelli.vacancy_management.modules.candidate.entities.CandidateEntity;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {  
  Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);
 
} 
