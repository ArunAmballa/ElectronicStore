package com.arun.dtos;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

	
	private String categoryId;
	
	@NotBlank
	@Min(value=4,message="titile must be of minimum 4 Characters")
	private String title;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String coverImage;
}
