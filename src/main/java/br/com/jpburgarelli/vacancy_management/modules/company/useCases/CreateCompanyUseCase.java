package br.com.jpburgarelli.vacancy_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jpburgarelli.vacancy_management.exception.ExceptionUserAlreadyFound;
import br.com.jpburgarelli.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.jpburgarelli.vacancy_management.modules.company.repository.CompanyRepository;

@Service
public class CreateCompanyUseCase {


  @Autowired
  private CompanyRepository companyRepository;

  public CompanyEntity execute(CompanyEntity companyEntity){

      this.companyRepository
        .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent((user) -> {
          throw new ExceptionUserAlreadyFound();
        });

      return this.companyRepository.save(companyEntity);
  }
  
}
