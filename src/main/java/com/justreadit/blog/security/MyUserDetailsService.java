package com.justreadit.blog.security;



import org.springframework.beans.factory.annotation.Autowired;

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
	public User loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
		
	  User u = userRepo.findByNameOrEmail(userNameOrEmail).orElseThrow(()-> new ResourceNotFoundException("User", "userNameOrEmail", userNameOrEmail));
	
      System.out.println("Email:"+u.getEmail()); 
      System.out.println("Password:"+u.getPassword());
      System.out.println("Roles:"+u.getRoles());
      
      return u;
	}

}
