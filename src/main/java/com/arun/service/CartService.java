package com.arun.service;

import com.arun.dtos.AddItemToCartRequest;
import com.arun.dtos.CartDto;

public interface CartService {
	
	//add item to cart
	//Case-1: Cart for user is not available then create a cart and add item
	//case-2 Cart available add the items to cart
	CartDto addItemToCart(String userId, AddItemToCartRequest request);
	
	
	void removeItemFromCart(String userId,Integer cartItem);
	
	
	void clearCart(String userId);
	
	
	CartDto  getCartByUSer(String userId);
	
	
}
