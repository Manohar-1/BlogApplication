package com.justreadit.blog.utils;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;

import com.justreadit.blog.repository.UserRepo;
import com.justreadit.blog.entities.User;

@Component
public class GetCurrentUserDetails {

	
	@Autowired
	private UserRepo userRepo;
	
	public String getCurrentUser() {
		SecurityContext sContext = SecurityContextHolder.getContext();
	    Authentication authentication = sContext.getAuthentication(); 
	    System.out.println(authentication.getName()); 
	    
	    
//	    System.out.println(userRepo);
	    Object principalObject = authentication.getPrincipal(); 
	    User principalUser = (User)principalObject;
	    System.out.println(principalUser.getEmail());
	    return principalUser.getEmail();
	}
	
}
