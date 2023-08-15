package com.justreadit.blog.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor 
@Getter
@Setter
public class CategoryDto {

	
	private Integer categoryId;  
	
	@NotBlank 
	@Size(min=4,message = "min categoryTitle length is 4")
	private String categoryTitle;  
	
	@NotBlank 
	@Size(min=4,max=20,message ="The minimum length of description is 4 and max is 20")
	private String categoryDescription; 
	
	
}
