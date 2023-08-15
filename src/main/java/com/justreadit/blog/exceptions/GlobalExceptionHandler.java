package com.justreadit.blog.exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.justreadit.blog.payload.APIResponse;
import java.util.HashMap;
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message = ex.getMessage(); 
		APIResponse apiresponse = new APIResponse(message,false); 
		return new ResponseEntity<>(apiresponse,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
		Map<String,String> resp = new HashMap<>(); 
		ex.getBindingResult().getAllErrors().forEach((error)->{;
		     String fieldName = ((FieldError)error).getField();
		     String message = error.getDefaultMessage(); 
		     resp.put(fieldName, message);
		});
		
		
		return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
	}
	
}
