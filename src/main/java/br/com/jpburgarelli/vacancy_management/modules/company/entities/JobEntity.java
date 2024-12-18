package br.com.jpburgarelli.vacancy_management.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


/*
 A anotação @JoinColumn é usada para definir como a chave estrangeira (foreign key) será 
 mapeada no banco de dados quando você está lidando com relacionamentos entre entidades.
 */

@Entity(name = "job")
@Data
public class JobEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String description;
  private String benefits;
  private String level;

  @ManyToOne()
  @JoinColumn(name = "company_id", insertable = false, updatable = false)
  private CompanyEntity companyEntity;
  
  @Column(name = "company_id")
  private UUID companyId;

  @CreationTimestamp
  private LocalDateTime createdAt;

  
}
