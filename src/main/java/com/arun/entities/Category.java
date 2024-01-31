package com.arun.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name="categories")
public class Category {
	
	@Id
	@Column(name="id")
	private String categoryId;
	
	@Column(name="category_title",length=60)
	private String title;
	
	@Column(name="category_desc",nullable=false)
	private String description;
	
	private String coverImage;
	
	@OneToMany(mappedBy="category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
//	@JsonIgnore
	private List<Product> products=new ArrayList<>();
	
	

}
