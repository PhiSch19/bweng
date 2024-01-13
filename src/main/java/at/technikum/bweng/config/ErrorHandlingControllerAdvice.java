package at.technikum.bweng.config;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handle(EntityNotFoundException exception) {
        return ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    }

}
