package br.com.jpburgarelli.vacancy_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jpburgarelli.vacancy_management.modules.company.entities.JobEntity;
import br.com.jpburgarelli.vacancy_management.modules.company.repository.JobRepository;

@Service
public class CreateJobUseCase {

  @Autowired
  private JobRepository jobRepository;

  public JobEntity execute(JobEntity jobEntity){
    return this.jobRepository.save(jobEntity);
  }
  
}
