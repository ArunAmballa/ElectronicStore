package com.arun.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

	
	private Integer cartItemId;
	

	private ProductDto productDto;
	
	
	private Integer quantity;
	
	
	private Integer totalPrice;

}
