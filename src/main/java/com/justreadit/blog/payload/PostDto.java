package com.justreadit.blog.payload;

import java.sql.Date;
import java.util.List;

import com.justreadit.blog.entities.Category;
import com.justreadit.blog.entities.Comment;
import com.justreadit.blog.entities.User;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private Integer postId;
	
	@NotEmpty
	private String title; 
	
	@NotEmpty
	private String content; 
	
	 
	private String imageName; 
	
	
	private Date addedDate;
	private CategoryDto category; 
	private UserDto user;
	private List<CommentDto> comments; 
}
