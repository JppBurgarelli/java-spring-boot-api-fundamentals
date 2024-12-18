package br.com.jpburgarelli.vacancy_management.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jpburgarelli.vacancy_management.modules.candidate.CandidateEntity;
import br.com.jpburgarelli.vacancy_management.modules.candidate.CandidateRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController { 
 
  // usada para realizar injeção de dependência de forma automática.
  @Autowired
  private CandidateRepository candidateRepository;

  @PostMapping("/")
  public CandidateEntity create(@Valid @RequestBody CandidateEntity candidateEntity){
    return this.candidateRepository.save(candidateEntity);
  }

  
}
