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
import com.arun.dtos.PageableResponse;
import com.arun.dtos.ProductDto;
import com.arun.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	//create
	
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto)
	{
		ProductDto createdProduct = productService.create(productDto);
		
		return new ResponseEntity<>(createdProduct,HttpStatus.CREATED);
		
	}
	
	
	
	//update
	@PutMapping("/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable String productId)
	{
		ProductDto updatedProduct = productService.update(productDto, productId);
		
		return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
		
	}
	
	
	//delete
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId)
	{
		productService.delete(productId);
		
		ApiResponseMessage response = ApiResponseMessage.builder()
		.message("Product Deleted Successfully") 
		.httpStatus(HttpStatus.OK)
		.success(true)
		.build();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	//getSingle
	@GetMapping("/{productId}")
	public ProductDto getById(@PathVariable String productId)
	{
		ProductDto product = productService.get(productId);
		
		return product;
		
	}
	
	
	//getall
	@GetMapping
	public ResponseEntity<PageableResponse<ProductDto>> getAll(
			@RequestParam(value="pageNumber",defaultValue="0",required=false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="sortBy",defaultValue="title",required=false) String  sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false) String  sortDir)
	{
		PageableResponse<ProductDto> response = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	//getall Live
	@GetMapping("/live")
	public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
			@RequestParam(value="pageNumber",defaultValue="0",required=false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="sortBy",defaultValue="title",required=false) String  sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false) String  sortDir)
	{
		PageableResponse<ProductDto> response = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	//search all
	
	@GetMapping("/search/{title}")
	public ResponseEntity<PageableResponse<ProductDto>> searchproduct(
			@RequestParam(value="pageNumber",defaultValue="0",required=false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue="10",required=false) int pageSize,
			@RequestParam(value="sortBy",defaultValue="title",required=false) String  sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false) String  sortDir,
			@PathVariable String title)
	{
		PageableResponse<ProductDto> response = productService.searchByTitle(title,pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	

}
