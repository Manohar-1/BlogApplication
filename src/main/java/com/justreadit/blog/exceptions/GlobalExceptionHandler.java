package com.justreadit.blog.exceptions;

import java.util.Map;

import org.apache.catalina.valves.rewrite.InternalRewriteMap.Escape;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
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
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<APIResponse> badCredentialsException(BadCredentialsException ex){
		String message = ex.getMessage(); 
		APIResponse apiresponse = new APIResponse(message,false);  
		return new ResponseEntity<APIResponse>(apiresponse,HttpStatusCode.valueOf(401));
	}
	
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<APIResponse> accessDenied(AccessDeniedException ex){
		String message = "Access Denied";
		String scenario = "POSSIBLE SCENARIOS:1)User cannot access this resource 2)Token might have been expired please login again."; 
		
	    APIResponse response = new APIResponse(message+" "+scenario,false); 
	    
	    return new ResponseEntity<APIResponse>(response,HttpStatus.CONFLICT); 
	}
	
}
