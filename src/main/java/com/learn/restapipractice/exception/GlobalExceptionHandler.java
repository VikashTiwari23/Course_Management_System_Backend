package com.learn.restapipractice.exception;

import com.learn.restapipractice.dto.ErrorDTO;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(errorDTO,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.CONFLICT.value(),ex.getMessage());
        return new ResponseEntity<>(errorDTO,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDTO>handleRuntimeException(RuntimeException ex){
        ErrorDTO errorDTO = new ErrorDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
        return new ResponseEntity<>(errorDTO,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
