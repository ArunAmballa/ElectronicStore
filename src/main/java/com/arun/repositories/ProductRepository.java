package com.arun.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arun.dtos.PageableResponse;
import com.arun.entities.Category;
import com.arun.entities.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, String>{
	
	Page<Product> findByTitleContaining(String title,Pageable pageable);
	
	Page<Product> findByLiveTrue(Pageable pageable);
	
	Page<Product> findByCategory(Category category,Pageable pageable);

}
