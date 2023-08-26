package com.justreadit.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justreadit.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
