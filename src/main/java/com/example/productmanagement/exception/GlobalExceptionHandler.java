package com.example.productmanagement.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.example.productmanagement.bean.Response;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(ProductException.class)
//    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors()
//                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
    
    @ExceptionHandler(ProductException.class)
	@ResponseBody
	public ResponseEntity<Response> handleProductException(ProductException ex) {
    	Response error = new Response(ex.getStatusCode(), ex.getMessage());
//        error.setErrorMessage(ex.getMessage());  // Send PMMessage.INVALID_JWT or TOKEN_EXPIRED
//        error.setErrorCode(HttpStatus.UNAUTHORIZED.value());  // 401 Unauthorized
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNotFound(NoSuchElementException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherErrors(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}