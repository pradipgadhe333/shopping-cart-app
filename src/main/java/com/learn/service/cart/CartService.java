package com.learn.service.cart;

import java.math.BigDecimal;

import com.learn.dto.CartDto;
import com.learn.model.Cart;
import com.learn.model.User;

public interface CartService {
	
	Cart getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalAmount(Long id);
	Cart initializeNewCart(User user);
	Cart getCartByUserId(Long userId);
	CartDto convertToDto(Cart cart);
	
}
