package com.arun.service.implementation;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arun.dtos.AddItemToCartRequest;
import com.arun.dtos.CartDto;
import com.arun.entities.Cart;
import com.arun.entities.CartItem;
import com.arun.entities.Product;
import com.arun.entities.User;
import com.arun.exceptions.ResourceNotFoundException;
import com.arun.repositories.CartItemRepository;
import com.arun.repositories.CartRepository;
import com.arun.repositories.ProductRepository;
import com.arun.repositories.UserRepository;
import com.arun.service.CartService;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CartItemRepository cartItemRepository;
 
	@Override
	public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
		
		int quantity = request.getQuantity();
		
		String productId = request.getProductId();
		
		Product product=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Doesnot exists"));
		
		//fetch user from DB
		
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Does Not Exists"));	
		
		Cart cart=null;
		try {
			
			cart=cartRepository.findByUser(user).get();
		}
		catch(NoSuchElementException e)
		{
			cart=new Cart();
			cart.setCartId(UUID.randomUUID().toString());
			cart.setCreatedAt(new Date());
		}
		
		//perform Cart Operation
		//if cartItem already Present then update
		 
		 
		 AtomicReference<Boolean> updated=new AtomicReference<>(false);
		
		 List<CartItem> items = cart.getItems();
		 
		 List<CartItem> updatedItems = items.stream().map(item->
		 {
			 if(item.getProduct().equals(productId))
			{
				 	item.setQuantity(quantity);
				 	item.setTotalPrice(quantity*product.getPrice());
				 	updated.set(true);
				 
			}
			 return item;
		}).collect(Collectors.toList());
		 
		 cart.setItems(updatedItems);
		
		//create Items
		 
		 
		if(!updated.get())
		{
			CartItem cartItem = CartItem.builder()
			.quantity(quantity)
			.totalPrice(quantity*product.getPrice())
			.cart(cart)
			.product(product)
			.build();
			
			cart.getItems().add(cartItem);
		}
		
		cart.setUser(user);
		
		Cart updatedCart = cartRepository.save(cart);
		
		return mapper.map(updatedCart, CartDto.class);
		
		
		
	}

	@Override
	public void removeItemFromCart(String userId, Integer cartItem) {
		
		CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(()->new ResourceNotFoundException("Cart Item does not exists"));
		cartItemRepository.delete(cartItem1);		
	}

	@Override
	public void clearCart(String userId) {
		
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Does Not Exists"));	
		
		Cart cart=cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Catrt Does not exists"));
		
		cart.getItems().clear();
		cartRepository.save(cart);
		
	}

	@Override
	public CartDto getCartByUSer(String userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Does Not Exists"));	
		
		Cart cart=cartRepository.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Catrt Does not exists"));
		return mapper.map(cart, CartDto.class);
	}
	
	

}
