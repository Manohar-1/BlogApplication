package com.justreadit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justreadit.blog.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{

	
	
}
