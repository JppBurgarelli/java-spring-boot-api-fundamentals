package br.com.jpburgarelli.vacancy_management.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageDTO {

  private String message;
  private String field;
  
}
