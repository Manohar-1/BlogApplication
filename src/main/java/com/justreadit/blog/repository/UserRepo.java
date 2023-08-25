package com.justreadit.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.justreadit.blog.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{	
	@Query("select u from User u where u.name = :key OR u.email = :key")
	Optional<User> findByNameOrEmail(@Param("key") String userNameOrEmail); 
}
