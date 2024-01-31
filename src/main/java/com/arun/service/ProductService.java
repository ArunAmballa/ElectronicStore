package com.arun.service;

import java.util.List;

import com.arun.dtos.PageableResponse;
import com.arun.dtos.ProductDto;
import com.arun.entities.Product;

public interface ProductService {
	
	//create
	ProductDto create(ProductDto productDto);
	
	//update
	ProductDto update(ProductDto productDto,String productId);
	
	//delete
	void delete(String productId);
	
	
	//get Single
	ProductDto get(String productId);
	
	
	//get All
	PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	
	
	//get All Live
	PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);
	
	
	
	//search product
	PageableResponse<ProductDto> searchByTitle(String title,int pageNumber,int pageSize,String sortBy,String sortDir);
	
	
	//create Product with Category
	
	ProductDto createWithCategory(ProductDto productDto,String CategoryId);
	
	ProductDto updateCategory(String productId,String categoryId);
	
	PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);
	
}
