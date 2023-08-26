package com.justreadit.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justreadit.blog.payload.APIResponse;
import com.justreadit.blog.payload.CategoryDto;
import com.justreadit.blog.service.CategoryService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	
	
	//create
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto); 
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED); 
	}
	
	//update 
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Integer categoryId){
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId); 
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK) ;
	}
	
	//delete  
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<APIResponse> deleteCategory(@PathVariable Integer categoryId){
		this.categoryService.deleteCategory(categoryId);  
		return new ResponseEntity<>(new APIResponse("CategoryID is deleted successfully...",true),HttpStatus.OK);
	}
	
	//get single 
	@PermitAll
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable Integer categoryId){
	    	CategoryDto cdto =  this.categoryService.getCategory(categoryId); 
	    	return ResponseEntity.ok(cdto); 
	}
	
	//get all
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		List<CategoryDto> categoryDtoList = this.categoryService.getAllCategory(); 
		
		return ResponseEntity.ok(categoryDtoList);
	}
	
}
