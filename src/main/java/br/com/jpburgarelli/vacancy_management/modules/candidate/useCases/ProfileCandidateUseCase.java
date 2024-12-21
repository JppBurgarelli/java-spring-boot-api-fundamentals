package br.com.jpburgarelli.vacancy_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.jpburgarelli.vacancy_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.jpburgarelli.vacancy_management.modules.candidate.repository.CandidateRepository;

@Service
public class ProfileCandidateUseCase {


  @Autowired
  private CandidateRepository candidateRepository;


  public ProfileCandidateResponseDTO execute(UUID candidateId){
    var ifCandidateExists = this.candidateRepository.findById(candidateId)
                                .orElseThrow(() -> {
                                  throw new UsernameNotFoundException(null);
                                });
    
    var candidateResponseDTO = ProfileCandidateResponseDTO.builder()
                                .name(ifCandidateExists.getName())
                                .email(ifCandidateExists.getEmail())
                                .username(ifCandidateExists.getUsername())
                                .description(ifCandidateExists.getDescription())
                                .id(ifCandidateExists.getId())
                                .build();

    return candidateResponseDTO;
                                
  }
}
