package com.arun.service.implementation;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.arun.dtos.CategoryDto;
import com.arun.dtos.PageableResponse;
import com.arun.entities.Category;
import com.arun.exceptions.ResourceNotFoundException;
import com.arun.helper.Helper;
import com.arun.repositories.CategoryRepository;
import com.arun.service.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper mapper;
	

	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		
		String categoryId=UUID.randomUUID().toString();
		
		categoryDto.setCategoryId(categoryId);
		
		Category category=mapper.map(categoryDto, Category.class);
		
		Category savedCategory=categoryRepository.save(category);
		
		return mapper.map(savedCategory, CategoryDto.class);
		
	}

	@Override
	public CategoryDto update(CategoryDto categoryDto, String categoryId) {
		
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category with gievn Id is Not Found"));
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		category.setCoverImage(categoryDto.getCoverImage());
		
		Category updatedCategory=categoryRepository.save(category);
		
		return mapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void delete(String categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category with gievn Id is Not Found"));
	    categoryRepository.delete(category);
		
	}

	@Override
	public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
		
		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
		
	    PageRequest pageable = PageRequest.of(pageNumber,pageSize,sort);
	    
		Page<Category>page=categoryRepository.findAll(pageable);
		
		PageableResponse<CategoryDto> response=Helper.getPageableResponse(page, CategoryDto.class);
		
		return response;
	}

	@Override
	public CategoryDto get(String categoryId) {
		
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category with gievn Id is Not Found"));
		
		return mapper.map(category, CategoryDto.class);
	}

 

}
