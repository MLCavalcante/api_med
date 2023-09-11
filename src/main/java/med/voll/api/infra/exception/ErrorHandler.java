package med.voll.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ConfirmationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity ErrorHandling404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity ErrorHandling400(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(errors.stream().map(ValidationDataError::new).toList());
    }

    @ExceptionHandler(ConfirmationException.class)
    public ResponseEntity businessLogicErrorHandling(ConfirmationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    public record ValidationDataError(String field, String message){

       public ValidationDataError(FieldError error){
           this(error.getField(), error.getDefaultMessage());
       }
    }

}
