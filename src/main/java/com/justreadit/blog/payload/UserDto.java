package com.justreadit.blog.payload;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@Getter 
@Setter
public class UserDto {

	private Integer id;
	
	@NotEmpty 
	@Size(min=4,message="Username must be of min 4 characters")
	private String name;  
	
	@Email(message="Email address is not valid")
	private String email;
	
	@NotEmpty
	@Size(min=4,max=10,message="Password must be of min 3 characters and max 10 characters")
	private String password; 
	
	@NotNull
	private String about; 
	
	private List<RoleDto> roles = new ArrayList<>();
}
