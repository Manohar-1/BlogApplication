package com.justreadit.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justreadit.blog.entities.Category;
import com.justreadit.blog.exceptions.ResourceNotFoundException;
import com.justreadit.blog.payload.CategoryDto;
import com.justreadit.blog.repository.CategoryRepo;
import com.justreadit.blog.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo; 
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category c = this.modelMapper.map(categoryDto,Category.class); 
		Category savedCategory = categoryRepo.save(c); 
		
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId) {
		Category c = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category ID",categoryId)); 
		
		Category updatedCategory = this.modelMapper.map(categoryDto, Category.class);  
		updatedCategory.setCategoryId(categoryId); 
		this.categoryRepo.save(updatedCategory);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
		
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category c = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category ID",categoryId)); 
        this.categoryRepo.delete(c); 
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category c = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category ID",categoryId)); 
		
		return this.modelMapper.map(c, CategoryDto.class); 
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categoryList = categoryRepo.findAll(); 
		
		List<CategoryDto> categoryDtoList = categoryList.stream().map((category) -> this.modelMapper.map(category,CategoryDto.class))
		.collect(Collectors.toList());
		
		return categoryDtoList;
	}

}
