package com.arun.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arun.dtos.AddItemToCartRequest;
import com.arun.dtos.ApiResponseMessage;
import com.arun.dtos.CartDto;
import com.arun.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@PostMapping("/{userId}")
	public ResponseEntity<CartDto> addItemToCart(@RequestBody AddItemToCartRequest request,@PathVariable String userId)
	{
		CartDto cartDto=cartService.addItemToCart(userId, request);
		
		return new ResponseEntity<>(cartDto,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{userId}/items/{itemId}")
	public ResponseEntity<ApiResponseMessage>  removeItemFromCart(@PathVariable String userId,@PathVariable Integer itemId)
	{
		cartService.removeItemFromCart(userId, itemId);
		ApiResponseMessage response = ApiResponseMessage.builder()
		.message("Item removed Successfully")
		.success(true)
		.httpStatus(HttpStatus.OK)
		.build();
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage>  clearCart(@PathVariable String userId)
	{
		cartService.clearCart(userId);
		ApiResponseMessage response = ApiResponseMessage.builder()
		.message("Now Cart is Blank")
		.success(true)
		.httpStatus(HttpStatus.OK)
		.build();
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId)
	{
		CartDto cartDto=cartService.getCartByUSer(userId);
		
		return new ResponseEntity<>(cartDto,HttpStatus.OK);
		
	}
	
	
	
	
	
	
}
