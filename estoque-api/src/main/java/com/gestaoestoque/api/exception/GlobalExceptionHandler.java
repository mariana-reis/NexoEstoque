package com.gestaoestoque.api.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(), 400, "Bad Request", "Erro de validação nos campos", request.getRequestURI(), errors
    );
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(), 404, "Not Found",
      ex.getMessage(), request.getRequestURI(), null
    );
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }
  
  @ExceptionHandler(EstoqueInsuficienteException.class)
  public ResponseEntity<ErrorResponse> handleEstoqueInsuficiente(EstoqueInsuficienteException ex, HttpServletRequest request) {

    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(), 422, "Unprocessable Entity",
      ex.getMessage(), request.getRequestURI(), null
    );
    return new ResponseEntity<>(error, HttpStatus.valueOf(422));
  }
  
  @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
  public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(org.springframework.dao.DataIntegrityViolationException ex, HttpServletRequest request) {

    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(), 409, "Conflict",
      "Não é possível excluir este produto pois ele possui movimentações de estoque registradas.",
      request.getRequestURI(), null
    );
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, HttpServletRequest request) {
    ErrorResponse error = new ErrorResponse(
      LocalDateTime.now(), 500,
      "Internal Server Error",
      "Ocorreu um erro inesperado no servidor: " + ex.getMessage(), request.getRequestURI(), null
    );
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
