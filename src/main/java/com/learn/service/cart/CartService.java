package com.learn.service.cart;

import java.math.BigDecimal;

import com.learn.model.Cart;

public interface CartService {
	
	Cart getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalAmount(Long id);
	Long initializeNewCart();
	
}
