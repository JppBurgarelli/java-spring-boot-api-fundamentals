package br.com.jpburgarelli.vacancy_management.modules.candidate.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String name;

  @Pattern(regexp = "^(?!\\s*$).+", message = "O campo [usarname] nao deve conter espaco")
  private String username;
  
  @Email(message = "O campo [email] deve conter um e-mail valido")
  private String email;

  @Length(min = 6, max = 100)
  private String password;
  private String description;
  private String resume;


  @CreationTimestamp
  private LocalDateTime createdAt;
  
}
