package br.com.jpburgarelli.vacancy_management.modules.candidate;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CandidateEntity {
  private UUID id;
  private String name;

  @Pattern(regexp = "^(?!\\s*$).+", message = "O campo [usarname] nao deve conter espaco")
  private String username;
  
  @Email(message = "O campo [email] deve conter um e-mail valido")
  private String email;

  @Length(min = 6, max = 24)
  private String password;
  private String description;
  private String resume;
  
}
