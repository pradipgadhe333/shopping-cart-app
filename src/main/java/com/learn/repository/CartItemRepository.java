package com.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	void deleteAllByCartId(Long id);
	
}
