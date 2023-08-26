package com.justreadit.blog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.justreadit.blog.config.AppConstants;
import com.justreadit.blog.entities.Role;
import com.justreadit.blog.repository.RoleRepo;
import com.justreadit.blog.security.MyUserDetailsService;


import java.util.List;

import org.modelmapper.ModelMapper;
@SpringBootApplication
public class BlogApplication implements CommandLineRunner{

	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	@Override
	public void run(String... args) throws Exception {
		try {
			Role r1 = new Role();  
			r1.setId(AppConstants.ROLE_ADMIN); 
			r1.setRole("ROLE_ADMIN");
			Role r2 = new Role(); 
			r2.setId(AppConstants.ROLE_USER); 
			r2.setRole("ROLE_USER"); 
		    List<Role> roles =  List.of(r1,r2); 
		    roleRepo.saveAll(roles); 
		}catch (Exception e) {
            e.printStackTrace();
		}
	} 
	
	

	
}
