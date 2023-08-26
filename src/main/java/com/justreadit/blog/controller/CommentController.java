package com.justreadit.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.justreadit.blog.payload.APIResponse;
import com.justreadit.blog.payload.CommentDto;
import com.justreadit.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	
	@Autowired
	private CommentService commentService;
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
	    CommentDto savedCommentDto =	this.commentService.createComment(commentDto, postId); 
		return new ResponseEntity<>(savedCommentDto,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<APIResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId); 
		return new ResponseEntity<>(new APIResponse("Comment Deleted successfully...",true),HttpStatus.OK); 
	}
	
}
