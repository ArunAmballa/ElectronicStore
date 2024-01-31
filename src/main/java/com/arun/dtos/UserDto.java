package com.arun.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	
	
	private String userId;
	
	@Size(min=3,max=15,message="Invalid Name")
	private String name;
	
//	@Email(message="Invalid User Email")
	@Pattern(regexp = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$",message="Invalid User Email")
	private String email;
	
	@NotBlank(message="Password is Required")
	private String password;
	
	@Size(min=4,max=6,message="Invalid Gender")
	private String gender;
	
	@NotBlank(message="About is Required")
	private String about;
	
	
	private String imageName;
	
}
