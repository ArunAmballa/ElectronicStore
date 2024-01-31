package com.arun.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arun.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

}
