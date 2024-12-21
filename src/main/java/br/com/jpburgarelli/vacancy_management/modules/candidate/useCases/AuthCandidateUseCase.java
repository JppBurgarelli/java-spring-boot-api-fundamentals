package br.com.jpburgarelli.vacancy_management.modules.candidate.useCases;

import java.time.Instant;
import java.util.Arrays;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.jpburgarelli.vacancy_management.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.jpburgarelli.vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.jpburgarelli.vacancy_management.modules.candidate.repository.CandidateRepository;
import java.time.Duration;

@Service
public class AuthCandidateUseCase {

  @Value("${security.token.secret.candidate}")
  private String secretKey;


  @Autowired
  private CandidateRepository candidateRepository;


  @Autowired
  private PasswordEncoder passwordEncoder;


  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
    System.err.println("useCase chamado");
    var ifCandidateExists = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
                                .orElseThrow(() -> {
                                    throw new UsernameNotFoundException("Username/password incorrect!");
                                });
    System.out.println("aqui01");

    var ifPassowrdMatches = this.passwordEncoder
                                .matches(authCandidateRequestDTO.password(), 
                                          ifCandidateExists.getPassword());

    System.out.println("aqui");
    if(!ifPassowrdMatches){
      throw new AuthenticationException();
    }


    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expiresIn = Instant.now().plus(Duration.ofMinutes(10));
    var token = JWT.create()
                  .withIssuer("JwtAuth")
                  .withSubject(ifCandidateExists.getId().toString())
                  .withClaim("roles", Arrays.asList("CANDIDATE"))
                  .withExpiresAt(Instant.now().plus(Duration.ofMinutes(10)))
                  .sign(algorithm);

    var authCandidateResponse = AuthCandidateResponseDTO.builder()
                              .access_token(token)
                              .expires_in(expiresIn.toEpochMilli())
                              .build();

    return authCandidateResponse;
  }
  
}
