package com.justreadit.blog.config;

import java.net.http.HttpRequest;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.justreadit.blog.security.MyUserDetailsService;

import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfiguration{

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
		
		http.csrf().disable()
		.authorizeHttpRequests() 
		.anyRequest().authenticated() 
		.and()
		.httpBasic(); 
		return http.build();
	}
	
	@Bean
    public AuthenticationManager authenticationManager() { 
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(); 
		provider.setUserDetailsService(userDetailsService); 
		provider.setPasswordEncoder(encodePassword());
        return new ProviderManager(Arrays.asList(provider));
    }
	
	@Bean
	public PasswordEncoder encodePassword() {
		return new BCryptPasswordEncoder();
	}
    
	
	

}
