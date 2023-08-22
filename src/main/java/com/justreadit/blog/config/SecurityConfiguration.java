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
		http
		.csrf().disable()
		.authorizeHttpRequests()
		.anyRequest().authenticated()
        .and() 
        .httpBasic()
        ;
		return http.build();
	}
	
	@Bean
	public PasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}

}
