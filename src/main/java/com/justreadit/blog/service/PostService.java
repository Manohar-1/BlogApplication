package com.justreadit.blog.service;

import java.util.List;

import com.justreadit.blog.entities.Post;
import com.justreadit.blog.payload.CategoryDto;
import com.justreadit.blog.payload.PostDto;
import com.justreadit.blog.payload.PostResponse;
import com.justreadit.blog.payload.UserDto;

public interface PostService {

	
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId); 
	
	PostDto updatePost(PostDto postDto,Integer postId); 
	
	void deletePost(Integer postId); 
	
	//get All posts by user
	List<PostDto> getPostByUser(Integer userId); 
	
	//get All Posts by category
	List<PostDto> getPostByCategory(Integer categoryId);  
	
	//get single post;  
	PostDto getPostById(Integer id);
	
	//get all post;
	PostResponse getAllPosts(Integer pageNumber,Integer recordsPerPage,String sortBy,String sortDirection);
	
	List<PostDto> searchPosts(String keyword); 
}
