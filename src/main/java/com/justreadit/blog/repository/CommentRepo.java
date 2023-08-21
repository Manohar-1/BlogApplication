package com.justreadit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justreadit.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

	
	
}
