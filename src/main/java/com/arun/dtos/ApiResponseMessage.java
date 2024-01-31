package com.arun.dtos;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseMessage {
	
	private String message;
	
	private boolean success;
	
	private HttpStatus httpStatus;
	
	

}
