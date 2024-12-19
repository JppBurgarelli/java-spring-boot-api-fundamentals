package br.com.jpburgarelli.vacancy_management.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jpburgarelli.vacancy_management.exception.ExceptionUserAlreadyFound;
import br.com.jpburgarelli.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.jpburgarelli.vacancy_management.modules.candidate.repository.CandidateRepository;

// Camada de servico, onde fica a regra de negocio
@Service
public class CreateCandidateUseCase {
  // usado para realizar injeção de dependência de forma automática.
  @Autowired
  private CandidateRepository candidateRepository;

  public CandidateEntity execute(CandidateEntity candidateEntity){
    this.candidateRepository.
      findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
      .ifPresent((user) -> {
        throw new ExceptionUserAlreadyFound();
      });
    return this.candidateRepository.save(candidateEntity);
  }  
}

/*
 ifPresent executa uma ação somente quando o valor está presente.

*/
