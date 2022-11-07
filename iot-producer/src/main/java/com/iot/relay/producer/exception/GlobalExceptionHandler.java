package com.iot.relay.producer.exception;

import java.util.Date;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * This method returns a ResponseEntity for writing to the response with a message converter
   * @param ex the {@link Exception}
   * @return {@link ErrorDetails} with a custom error details
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globleExcpetionHandler(Exception ex) {
    ErrorDetails errorDetails = ErrorDetails
      .builder()
      .timestamp(new Date())
      .message(ex.getMessage())
      .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * This method returns a ResponseEntity for writing to the response with a message converter
   * @param ex the {@link ConstraintViolationException}
   * @return {@link ErrorDetails} with a custom error details
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> ConstraintViolationExceptionHandler(ConstraintViolationException ex) {
    ErrorDetails errorDetails = ErrorDetails
      .builder()
      .timestamp(new Date())
      .message("Invalid parameters")
      .errorFields(ex
          .getConstraintViolations()
          .stream()
          .map(fields -> fields.getPropertyPath().toString())
          .collect(Collectors.toList())
      )
      .build();
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
