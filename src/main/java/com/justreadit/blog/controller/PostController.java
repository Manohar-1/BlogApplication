package com.justreadit.blog.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.justreadit.blog.config.AppConstants;
import com.justreadit.blog.entities.Post;
import com.justreadit.blog.payload.APIResponse;
import com.justreadit.blog.payload.PostDto;
import com.justreadit.blog.payload.PostResponse;
import com.justreadit.blog.service.FileService;
import com.justreadit.blog.service.PostService;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService; 
	
	@Autowired
	private FileService fileService; 
	
	@Value("${project.image}")
	private String path;
	
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId){
	   PostDto createPost = this.postService.createPost(postDto, userId, categoryId); 
	   return new ResponseEntity<>(createPost,HttpStatus.CREATED); 
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostDto> postDtos = this.postService.getPostByUser(userId); 
		return  ResponseEntity.ok(postDtos);
	} 
	
	@PermitAll
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> postDtos = this.postService.getPostByCategory(categoryId); 
		return ResponseEntity.ok(postDtos);
	} 
	
	@PermitAll
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber",defaultValue =AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
			                                         @RequestParam(value = "recordsPerPage",defaultValue = AppConstants.RECORDS_PER_PAGE,required = false) Integer recordsPerPage,
			                                         @RequestParam(value="sortBy",defaultValue =AppConstants.SORT_BY,required = false) String sortBy, 
			                                         @RequestParam(value="sortDirection",defaultValue =AppConstants.SORT_DIRECTION,required = false) String sortDirection){
		
		PostResponse postResponse = this.postService.getAllPosts(pageNumber,recordsPerPage,sortBy,sortDirection); 
		return ResponseEntity.ok(postResponse); 
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostById(postId); 
		return ResponseEntity.ok(postDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("/posts/search/{keyword}") 
	public ResponseEntity<List<PostDto>> getPostBySearch(@PathVariable String keyword){
		System.out.println("Manohar");
		System.out.println(keyword);
		List<PostDto> postDtos = this.postService.searchPosts(keyword);
		return ResponseEntity.ok(postDtos);
	}
	
	//delete
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<APIResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId); 
		return ResponseEntity.ok(new APIResponse("Post deleted successfully...",true));
	}
	//update 
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/posts/{postId}") 
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId){
		PostDto updatedPostDto =  this.postService.updatePost(postDto, postId); 
		return ResponseEntity.ok(updatedPostDto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException{
		String fileName = this.fileService.uploadImage(path, image); 
		PostDto postDto = this.postService.getPostById(postId); 
		postDto.setImageName(fileName);  
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatedPost);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName,HttpServletResponse response) throws IOException {
		InputStream iStream = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(iStream, response.getOutputStream()); 
	}
	
}
