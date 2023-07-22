package com.crestdevs.sphinxbe.exception;


import com.crestdevs.sphinxbe.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {

        String message = ex.getMessage();

        ApiResponse response = new ApiResponse(message, false);

        return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
    }
}
