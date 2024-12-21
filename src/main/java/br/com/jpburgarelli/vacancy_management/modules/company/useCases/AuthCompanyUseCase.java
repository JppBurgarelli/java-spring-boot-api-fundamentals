package br.com.jpburgarelli.vacancy_management.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.jpburgarelli.vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.jpburgarelli.vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.jpburgarelli.vacancy_management.modules.company.repository.CompanyRepository;

@Service
public class AuthCompanyUseCase {

  @Value("${security.token.secret}")
  private String secretKey;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCandidateResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

    var ifCompanyExists = this.companyRepository
                          .findByUsername(authCompanyDTO.getUsername())
                          .orElseThrow(() -> {
                            throw new UsernameNotFoundException("Company not found!");
                          });
    
    var ifPasswordMatches = this.passwordEncoder
                            .matches(authCompanyDTO.getPassword(), ifCompanyExists.getPassword());
    
    if(!ifPasswordMatches){
      throw new AuthenticationException();
    }


    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var token = JWT.create()
                  .withIssuer("JwtAuth")
                  .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                  .withSubject(ifCompanyExists.getId().toString())
                  .withClaim("roles", Arrays.asList("candidate"))
                  .withExpiresAt(Instant.now().plus(Duration.ofMinutes(10)))
                  .sign(algorithm);

    var authCandidateResponse = AuthCandidateResponseDTO.builder()
                                  .access_token(token)
                                  .build();
    
    return authCandidateResponse;
  } 
}
