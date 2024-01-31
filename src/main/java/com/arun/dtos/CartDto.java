package com.arun.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
	
	
	private String cartId;
	
	private Date createdAt;
	
	
	private UserDto userDto;
	
	private List<CartItemDto> items=new ArrayList<>();
}
