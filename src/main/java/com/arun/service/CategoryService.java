package com.arun.service;

import com.arun.dtos.CategoryDto;
import com.arun.dtos.PageableResponse;

public interface CategoryService {

	//create
	CategoryDto create(CategoryDto categoryDto);
	
	
	//update
	CategoryDto update(CategoryDto categoryDto,String categoryId);
	
	
	//delete
	void delete(String categoryId);
	
	

	
	
	//getsingle
	
	CategoryDto get(String categoryId);


	PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
	
}
