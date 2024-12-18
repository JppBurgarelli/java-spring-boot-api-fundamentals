package br.com.jpburgarelli.vacancy_management.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

/*

Classes anotadas com @ControllerAdvice aplicam seus métodos aos controladores em 
toda a aplicação, a menos que sejam configuradas para escopos específicos 
(usando filtros como base de pacotes, classes específicas ou anotações).

A classe ExceptionHandlerController serve para centralizar o tratamento de exceções 
que podem ocorrer em diferentes controladores da aplicação. Ela intercepta e lida com 
essas exceções de maneira organizada.

*/
@ControllerAdvice
public class ExceptionHandlerController {

  private MessageSource messageSource;

  public ExceptionHandlerController(MessageSource message){
    this.messageSource = message;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ErrorMessageDTO>> MethodArgumentNotValidException(MethodArgumentNotValidException e){

  List<ErrorMessageDTO> dto = new ArrayList<>(); // Cria uma lista para armazenar as mensagens de erro

  e.getBindingResult().getFieldErrors().forEach(err -> {
    // Para cada erro de validação encontrado:
    String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
    // Busca a mensagem de erro no arquivo de mensagens no idioma atual
    
    ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());
    // Cria um objeto DTO com a mensagem de erro e o campo que falhou
    
    dto.add(error); // Adiciona o erro à lista
  });

    return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);

  }
  
}
