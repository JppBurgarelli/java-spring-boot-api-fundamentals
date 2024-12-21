package br.com.jpburgarelli.vacancy_management.modules.candidate.useCases;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.jpburgarelli.vacancy_management.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.jpburgarelli.vacancy_management.modules.candidate.repository.CandidateRepository;

@Service
public class AuthCandidateUseCase {

  @Value("${security.token.secret.candidate}")
  private String secretKey;


  @Autowired
  private CandidateRepository candidateRepository;


  @Autowired
  private PasswordEncoder passwordEncoder;


  public void execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
    var ifCandidateExists = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                        .orElseThrow(() -> {
                          throw new UsernameNotFoundException("Username/password incorrect!");
                        });

    var ifPassowrdMatche = this.passwordEncoder.matches(authCandidateRequestDTO.passowrd(), ifCandidateExists.getPassword());

    if(!ifPassowrdMatche){
      throw new AuthenticationException();
    }


    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    var token = JWT.create()
                    .withIssuer("JwtAuth")
                    .withSubject(ifCandidateExists.getId().toString())
                    .sign(algorithm);



  }
  
}
