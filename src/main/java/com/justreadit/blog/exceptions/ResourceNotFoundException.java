package com.justreadit.blog.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	String resourceName; 
	String resourceField; 
	long fieldValue;
	
	
	public ResourceNotFoundException(String resourceName, String resourceField, long fieldValue) {
		super(resourceName+" not found with " + resourceField+":"+fieldValue);
		this.resourceName = resourceName;
		this.resourceField = resourceField;
		this.fieldValue = fieldValue;
	} 
	
	
}
