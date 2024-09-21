package com.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	Cart findByUserId(Long userId);
	
}
