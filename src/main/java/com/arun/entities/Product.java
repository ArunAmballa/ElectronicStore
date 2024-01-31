package com.arun.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="products")
public class Product {
	
	@Id
	private String productId;
	
	private String title;
	
	@Column(length=1000)
	private String description;
	
	private int price;
	
	private int discountedPrice;
	
	private int quantity;
	
	private Date addedDate;
	
	private boolean live;
	
	private boolean stock;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="category_id")
	private Category category;
	
	
}
