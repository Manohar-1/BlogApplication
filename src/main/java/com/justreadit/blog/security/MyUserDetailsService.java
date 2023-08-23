package com.justreadit.blog.security;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.justreadit.blog.entities.User;
import com.justreadit.blog.exceptions.ResourceNotFoundException;
import com.justreadit.blog.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public User loadUserByUsername(String email) throws UsernameNotFoundException {
		
	  User u = userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User", "Email", email)); 
	
      System.out.println("Email:"+u.getEmail()); 
      System.out.println("Password:"+u.getPassword());
      
      return u;
	}

}
