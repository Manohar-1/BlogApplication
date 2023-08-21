package com.justreadit.blog.service;

import com.justreadit.blog.payload.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto,Integer postId); 
	void deleteComment(Integer commentId); 
	
	
	
}
