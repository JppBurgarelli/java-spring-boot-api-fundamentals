package br.com.jpburgarelli.vacancy_management.exception;

public class ExceptionUserAlreadyFound extends RuntimeException {
  public ExceptionUserAlreadyFound(){
    super("Usuario ja existente! ");
  }
  
}
