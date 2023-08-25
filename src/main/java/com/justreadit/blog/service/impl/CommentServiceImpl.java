package com.justreadit.blog.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justreadit.blog.entities.Comment;
import com.justreadit.blog.entities.Post;
import com.justreadit.blog.exceptions.ResourceNotFoundException;
import com.justreadit.blog.payload.CommentDto;
import com.justreadit.blog.repository.CommentRepo;
import com.justreadit.blog.repository.PostRepo;
import com.justreadit.blog.repository.UserRepo;
import com.justreadit.blog.service.CommentService;
import com.justreadit.blog.utils.GetCurrentUserDetails;
import com.justreadit.blog.entities.User;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo; 
	
	@Autowired
	private ModelMapper modelMapper; 
	
	@Autowired
	private UserRepo userRepo;
	
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		
		Comment comment = this.modelMapper.map(commentDto,Comment.class); 
		
		post.getComments().add(comment); 
		
		comment.setPost(post);
		String currentUserEmail = new GetCurrentUserDetails().getCurrentUser();
		User commentedUser = userRepo.findByNameOrEmail(currentUserEmail).get();  
		comment.setUser(commentedUser);
		
		
		Comment savedComment = commentRepo.save(comment) ; 
		
		
		
		
		
		return this.modelMapper.map(savedComment,CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "CommentID", commentId)); 
		this.commentRepo.delete(comment);
		
		
	}

}
