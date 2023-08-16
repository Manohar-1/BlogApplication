package com.justreadit.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.justreadit.blog.payload.APIResponse;
import com.justreadit.blog.payload.PostDto;
import com.justreadit.blog.payload.PostResponse;
import com.justreadit.blog.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId){
	   PostDto createPost = this.postService.createPost(postDto, userId, categoryId); 
	   return new ResponseEntity<>(createPost,HttpStatus.CREATED); 
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostDto> postDtos = this.postService.getPostByUser(userId); 
		return  ResponseEntity.ok(postDtos);
	} 
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> postDtos = this.postService.getPostByCategory(categoryId); 
		return ResponseEntity.ok(postDtos);
	} 
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue ="0",required = false) Integer pageNumber,
			                                         @RequestParam(value = "recordsPerPage",defaultValue = "10",required = false) Integer recordsPerPage,
			                                         @RequestParam(value="sortBy",defaultValue ="postId",required = false) String sortBy, 
			                                         @RequestParam(value="sortDirection",defaultValue = "asc",required = false) String sortDirection){
		
		PostResponse postResponse = this.postService.getAllPosts(pageNumber,recordsPerPage,sortBy,sortDirection); 
		return ResponseEntity.ok(postResponse); 
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostById(postId); 
		return ResponseEntity.ok(postDto);
	}
	
	@GetMapping("/posts/search/{keyword}") 
	public ResponseEntity<List<PostDto>> getPostBySearch(@PathVariable String keyword){
		System.out.println("Manohar");
		System.out.println(keyword);
		List<PostDto> postDtos = this.postService.searchPosts(keyword);
		return ResponseEntity.ok(postDtos);
	}
	
	//delete
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<APIResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId); 
		return ResponseEntity.ok(new APIResponse("Post deleted successfully...",true));
	}
	//update 
	@PutMapping("/posts/{postId}") 
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatedPostDto =  this.postService.updatePost(postDto, postId); 
		return ResponseEntity.ok(updatedPostDto);
	}
	
}
