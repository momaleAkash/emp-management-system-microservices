package com.em.performance_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> invalidMethodArgumentException(MethodArgumentNotValidException e){
        Map<String,String>map=new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error->map.put(error.getField(),error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(InvalidEvaluationStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEvaluationStatusException(InvalidEvaluationStatusException e){
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid Evaluation Status");
    }

    @ExceptionHandler(PerformanceAccountDoesntExist.class)
    public ResponseEntity<ErrorResponse> handlePerformanceAccountDoesntExist(PerformanceAccountDoesntExist e){
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request parameter: " + e.getName());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Invalid request body");
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(Instant.now(), status.value(), status.getReasonPhrase(), message));
    }
}
