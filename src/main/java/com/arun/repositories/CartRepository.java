package com.arun.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arun.entities.Cart;
import com.arun.entities.User;

public interface CartRepository extends JpaRepository<Cart, String>{

	
	Optional<Cart> findByUser(User user);
}
