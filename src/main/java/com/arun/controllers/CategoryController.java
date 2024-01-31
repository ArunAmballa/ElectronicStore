package com.arun.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arun.dtos.ApiResponseMessage;
import com.arun.dtos.CategoryDto;
import com.arun.dtos.PageableResponse;
import com.arun.dtos.ProductDto;
import com.arun.service.CategoryService;
import com.arun.service.ProductService;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	//create
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	
	@PostMapping()
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto)
	{
		
		CategoryDto categoryDto1=categoryService.create(categoryDto);
		
		return new ResponseEntity<>(categoryDto1,HttpStatus.CREATED);
		
	}
	
	
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable String categoryId)
	{
		
		CategoryDto updatedCategory=categoryService.update(categoryDto, categoryId);
		
		return new ResponseEntity<>(updatedCategory,HttpStatus.CREATED);
	}
	
	
	//delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId)
	{
		categoryService.delete(categoryId);
		ApiResponseMessage message = ApiResponseMessage.builder().message("Category Deleted Successfully")
		.httpStatus(HttpStatus.OK)
		.success(true)
		.build();
		
		return new ResponseEntity<>(message,HttpStatus.OK);
		
	}
	
	
	//getall
    @GetMapping
	public ResponseEntity<PageableResponse<CategoryDto>> getAll(
			@RequestParam(value="pageNumber",defaultValue="0",required=false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="sortBy",defaultValue="title",required=false) String  sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false) String  sortDir)
	{
		
		PageableResponse<CategoryDto> response = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	//get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getBy(@PathVariable String categoryId)
	
	{
		CategoryDto categoryDto=categoryService.get(categoryId);
		
		return new ResponseEntity<>(categoryDto,HttpStatus.OK);
	}
	
	//create Product with Category
	@PostMapping("/{categoryId}/products")
	public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable String categoryId,@RequestBody ProductDto productDto)
	{
		ProductDto response = productService.createWithCategory(productDto, categoryId);
		
		return new ResponseEntity<>(response,HttpStatus.CREATED);
		
		
	}
	
	//update Category of Product
	@PutMapping("/{categoryId}/products/{productId}")
	public ResponseEntity<ProductDto> updateCategoryOfProduct(@PathVariable String categoryId,@PathVariable String productId)
	{
		ProductDto productDto = productService.updateCategory(productId, categoryId);
		
		return new ResponseEntity<>(productDto,HttpStatus.OK);
	}
	
	@GetMapping("/{categoryId}/products")
	public ResponseEntity<PageableResponse<ProductDto>> fetchingProducts(@PathVariable String categoryId,
			@RequestParam(value="pageNumber",defaultValue="0",required=false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="sortBy",defaultValue="title",required=false) String  sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false) String  sortDir)
	{
		PageableResponse<ProductDto> response = productService.getAllOfCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
		
		
	}
	
	
	

}
