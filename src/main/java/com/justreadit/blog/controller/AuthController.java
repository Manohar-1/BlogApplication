package com.justreadit.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justreadit.blog.security.JwtAuthRequest;
import com.justreadit.blog.security.JwtAuthResponse;
import com.justreadit.blog.security.JwtTokenHelper;
import com.justreadit.blog.security.MyUserDetailsService;

@RestController 
@RequestMapping("/api/v1/auth")
public class AuthController {
 
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private MyUserDetailsService userDetailsService; 
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request){
		 
		System.out.println(request.getUserName()+" "+request.getPassword());
		
		
		this.authenticate(request.getUserName(),request.getPassword()); 
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserName());  
		
		System.out.println("HERE:"+userDetails); 
		
		
		String token = this.jwtTokenHelper.generateToken(userDetails); 
		System.out.println(token);
		JwtAuthResponse response = new JwtAuthResponse();  
		response.setToken(token); 
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
	}

	private void authenticate(String userName, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName,password); 
		this.authenticationManager.authenticate(authenticationToken); 
	}
}
