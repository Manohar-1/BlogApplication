package com.justreadit.blog.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.asm.Advice.This;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.justreadit.blog.entities.Category;
import com.justreadit.blog.entities.Post;
import com.justreadit.blog.entities.User;
import com.justreadit.blog.exceptions.ResourceNotFoundException;
import com.justreadit.blog.payload.PostDto;
import com.justreadit.blog.payload.PostResponse;
import com.justreadit.blog.repository.CategoryRepo;
import com.justreadit.blog.repository.PostRepo;
import com.justreadit.blog.repository.UserRepo;
import com.justreadit.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo postRepo; 
	@Autowired
	private UserRepo userRepo; 
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper; 
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "UserID", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryID", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class); 
		post.setImageName("default.png"); 
		post.setAddedDate(new Date());
		post.setUser(user); 
		post.setCategory(category);
		
		Post savedPost = this.postRepo.save(post); 		 
		System.out.println(savedPost.getImageName()); 
		System.out.println(post.getImageName());
		return this.modelMapper.map(savedPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
         
		Post savedPost = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Posts", "PostID", postId));
		
		savedPost.setTitle(postDto.getTitle()); 
		savedPost.setImageName(postDto.getImageName()); 
		savedPost.setContent(postDto.getContent()); 
		
		return this.modelMapper.map(this.postRepo.save(savedPost), PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post savedPost = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Posts", "PostID", postId));

		this.postRepo.delete(savedPost); 
	}

	

	
	@Override
	public PostResponse getAllPosts(Integer pageNumber,Integer recordsPerPage,String sortBy,String sortDirection) {
		
		Sort sort = sortDirection.equalsIgnoreCase("ASC")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending(); 
	    
	    Page<Post> postPage =  this.postRepo.findAll(PageRequest.of(pageNumber, recordsPerPage,sort)); 
	    
	    List<Post> posts = postPage.getContent(); 
		
		List<PostDto> postDtos =  posts.stream().map(post-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());		
		PostResponse postResponse = new PostResponse(); 
		postResponse.setContent(postDtos); 
		postResponse.setPageNumber(postPage.getNumber());
		postResponse.setPageSize(postPage.getSize());
		postResponse.setTotalElements(postPage.getTotalElements());
		postResponse.setTotalPages(postPage.getTotalPages()); 
		postResponse.setLastPage(postPage.isLast());
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "UserID", userId));
	    List<Post> posts = this.postRepo.findByUser(user); 	    
	    List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());	    
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryID", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList()) ;
		return postDtos;
	}

	@Override
	public PostDto getPostById(Integer id) {
		Post post = this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "PostID", id));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos =  posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
