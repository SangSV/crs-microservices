package vn.edu.crs.authservice.exception; 

import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.MethodArgumentNotValidException; 
import org.springframework.web.bind.annotation.ExceptionHandler; 
import org.springframework.web.bind.annotation.RestControllerAdvice; 

import java.util.HashMap; 
import java.util.Map; 

@RestControllerAdvice 
public class GlobalExceptionHandler { 

    @ExceptionHandler(InvalidCredentialsException.class) 
    public ResponseEntity<Map<String, String>> handleInvalidCredentials(InvalidCredentialsException ex) { 
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", ex.getMessage())); 
    } 

    @ExceptionHandler(MethodArgumentNotValidException.class) 
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) { 
        Map<String, String> errors = new HashMap<>(); 
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->  
            errors.put(fieldError.getField(), fieldError.getDefaultMessage()) 
        ); 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors); 
    } 

    @ExceptionHandler(Exception.class) 
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) { 
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", ex.getMessage())); 
    } 
} 
