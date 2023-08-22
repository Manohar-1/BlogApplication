package com.justreadit.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.justreadit.blog.entities.User;
import com.justreadit.blog.exceptions.ResourceNotFoundException;
import com.justreadit.blog.repository.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService {

	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
	  User u = userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User", "Email", email)); 
	  
	  System.out.println(u.getUsername());
	  System.out.println(u.getEmail());
	  System.out.println(u.getUsername());
	  System.out.println(u.getUsername());
	  System.out.println(u.getUsername());
	  System.out.println(u.getUsername());
	  System.out.println(u.getUsername());
	  System.out.println(u.getUsername());
	  System.out.println(u.getUsername());
	  System.out.println(u.getUsername());
	  System.out.println(u.getUsername());
	  System.out.println(u.getUsername());
	  
	  return u;
	  
	}

}
