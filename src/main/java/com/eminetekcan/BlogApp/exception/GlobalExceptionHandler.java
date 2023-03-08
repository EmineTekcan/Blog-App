package com.eminetekcan.BlogApp.exception;

import com.eminetekcan.BlogApp.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){

        String message=exception.getMessage();
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setMessage(message);
        apiResponse.setSuccess(false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        Map<String, String> err=new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(objectError -> {
            String fieldName=((FieldError) objectError).getField();
            String message=objectError.getDefaultMessage();
            err.put(fieldName,message);
        });
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }
}
