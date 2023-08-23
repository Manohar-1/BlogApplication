package com.justreadit.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.justreadit.blog.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{
	Optional<User> findByEmail(String email);
	
	
}
