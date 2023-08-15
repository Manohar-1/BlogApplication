package com.justreadit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justreadit.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category,Integer>{

}
