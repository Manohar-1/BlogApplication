package com.justreadit.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	public ResourceNotFoundException(String resourceName, String resourceField, long fieldValue) {
		super(resourceName+" not found with " + resourceField+":"+fieldValue);
	}
	
	public ResourceNotFoundException(String resourceName,String resourceField,String fieldName) {
		super(resourceName+" not found with "+ resourceField+":"+fieldName); 
	}
}
