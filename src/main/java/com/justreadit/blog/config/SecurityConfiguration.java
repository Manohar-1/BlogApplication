package com.justreadit.blog.config;

import java.net.http.HttpRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests((auth)->auth
				.requestMatchers("/api/user/{userId}/category/{categoryId}/post","/api/posts/{postId}","/api/users/{userId}","/api/post/{postId}/comments","/api/comments/{commentId}").authenticated()
				.requestMatchers("/api/categories/","/api/categories/{categoryId}").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET,"/api/users/").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET,"/api/categories/","/api/categories/{categoryId}","/api/user/{userId}","/api/user/{userId}/posts","/api/category/{categoryId}/posts","/api/posts").permitAll() 
				.requestMatchers(HttpMethod.POST,"/api/users/").permitAll()
				.anyRequest().permitAll()
		)
		.csrf().disable()
		.httpBasic();

		return http.build();
	}
	
	@Bean
	public PasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}

}
