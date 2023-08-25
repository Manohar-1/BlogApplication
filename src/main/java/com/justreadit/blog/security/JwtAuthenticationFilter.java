package com.justreadit.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.justreadit.blog.repository.UserRepo;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		
		String requestToken = request.getHeader("Authorization"); 
		String userName=null; 
		String token=null; 
		
		if(requestToken!=null && requestToken.startsWith("Bearer")) {
		   token = requestToken.substring(7);  
		   
		   try {
			   userName = this.jwtTokenHelper.getUserNameFromToken(token); 
		   }catch(IllegalArgumentException e) {
			   System.out.println("Unable to get JWT token");
		   }catch (ExpiredJwtException e) {
			   System.out.println("Jwt token has expired");
		   }catch(MalformedJwtException e) {
		       System.out.println("Invalid JWT");
		   }
		}else {
			System.out.println("Jwt Token does not begin with Bearer");
		}
		
		//once we get the token, we validate the token 
		
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null ) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName); 
			 
			
			if(this.jwtTokenHelper.validateToken(token, userDetails)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities()); 
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken );
			}else {
				System.out.println("Invalid JWT Token"); 
			}
		}else {
			System.out.println("Username is null or context is null");
		} 
		
		filterChain.doFilter(request, response);
	}

}
