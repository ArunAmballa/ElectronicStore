package com.arun.dtos;

import java.util.Date;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductDto {

	
	private String productId;
	
	private String title;
	
	private String description;
	
	private int price;
	
	private int discountedPrice;
	
	private int quantity;
	
	private Date addedDate;
	
	private boolean live;
	
	private boolean stock;
	
	private CategoryDto category;
}
