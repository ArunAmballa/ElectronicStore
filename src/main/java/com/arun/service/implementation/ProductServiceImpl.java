package com.arun.service.implementation;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.arun.dtos.CategoryDto;
import com.arun.dtos.PageableResponse;
import com.arun.dtos.ProductDto;
import com.arun.entities.Category;
import com.arun.entities.Product;
import com.arun.exceptions.ResourceNotFoundException;
import com.arun.helper.Helper;
import com.arun.repositories.CategoryRepository;
import com.arun.repositories.ProductRepository;
import com.arun.service.ProductService;


@Service
public class ProductServiceImpl  implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public ProductDto create(ProductDto productDto) {
		
		String productId=UUID.randomUUID().toString();
		productDto.setProductId(productId);
		
		productDto.setAddedDate(new Date());
		
		Product product=mapper.map(productDto, Product.class);
		Product savedProduct = productRepository.save(product);
		return mapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public ProductDto update(ProductDto productDto, String productId) {
		
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with gievn id doesnot exists"));
		product.setDescription(productDto.getDescription());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setPrice(productDto.getPrice());
		product.setTitle(productDto.getTitle());
		product.setQuantity(productDto.getQuantity());
		product.setLive(productDto.isLive());
		product.setStock(productDto.isStock());
	
		Product updatedProduct = productRepository.save(product);
		
		return mapper.map(updatedProduct, ProductDto.class);
	}

	@Override
	public void delete(String productId) {
		
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with gievn id doesnot exists"));
		
		productRepository.deleteById(productId);
		
	}

	@Override
	public ProductDto get(String productId) {
		Product product = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product with gievn id doesnot exists"));
		return mapper.map(product, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        
		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
		
	    PageRequest pageable = PageRequest.of(pageNumber,pageSize,sort);
	    
		Page<Product>page=productRepository.findAll(pageable);
		
		PageableResponse<ProductDto> response=Helper.getPageableResponse(page, ProductDto.class);
		
		return response;
	}

	@Override
	public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
        
		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
		
	    PageRequest pageable = PageRequest.of(pageNumber,pageSize,sort);
	    
		Page<Product>page=productRepository.findByLiveTrue(pageable);
		
		PageableResponse<ProductDto> response=Helper.getPageableResponse(page, ProductDto.class);
		
		return response;
		
		
	}

	@Override
	public PageableResponse<ProductDto> searchByTitle(String title,int pageNumber,int pageSize,String sortBy,String sortDir) {
        
		Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
		
	    PageRequest pageable = PageRequest.of(pageNumber,pageSize,sort);
	    
		Page<Product>page=productRepository.findByTitleContaining(title,pageable);
		
		PageableResponse<ProductDto> response=Helper.getPageableResponse(page, ProductDto.class);
		
		return response;
	}

	@Override
	public ProductDto createWithCategory(ProductDto productDto,String categoryId) {
		
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Categpry Does not exists"));
		String productId=UUID.randomUUID().toString();
		productDto.setProductId(productId);
		productDto.setAddedDate(new Date());
		Product product=mapper.map(productDto, Product.class);
		product.setCategory(category);
		Product savedProduct = productRepository.save(product);
		return mapper.map(savedProduct, ProductDto.class);
		
	}

	@Override
	public ProductDto updateCategory(String productId, String categoryId) {
		
		Product productResponse = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product ID doesnot exists"));
		Category categoryResponse = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category Doesnot Exists"));
		productResponse.setCategory(categoryResponse);
		Product savedProduct=productRepository.save(productResponse);
		return  mapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Categpry Does not exists"));
		
	    Sort sort=(sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());
		
	    PageRequest pageable = PageRequest.of(pageNumber,pageSize,sort);
		Page<Product> page=productRepository.findByCategory(category,pageable);
		
		return Helper.getPageableResponse(page, ProductDto.class);
        
		
	}
	
	

}
