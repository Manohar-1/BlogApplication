package com.justreadit.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.justreadit.blog.entities.Category;
import com.justreadit.blog.entities.Post;
import com.justreadit.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> ,PagingAndSortingRepository<Post, Integer>{

	List<Post> findByUser(User u); 
	List<Post> findByCategory(Category c); 
	
	
	@Query("select p from Post p where title like :key")
	List<Post> searchByTitle(@Param("key") String title); 
	
}
