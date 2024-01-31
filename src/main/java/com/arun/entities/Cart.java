package com.arun.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name="cart")
public class Cart {
	
	@Id
	private String cartId;
	
	private Date createdAt;
	
	@OneToOne
	private User user;
	
	@OneToMany(mappedBy="cart",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<CartItem> items=new ArrayList<>();
	
	

}
