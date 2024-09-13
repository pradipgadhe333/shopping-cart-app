package com.learn.service.cart;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.learn.exception.ResourceNotFoundException;
import com.learn.model.Cart;
import com.learn.repository.CartItemRepository;
import com.learn.repository.CartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	
	//dependency injection
	private final CartRepository cartRepository; 
	private final CartItemRepository cartItemRepository;
	private final AtomicLong cartIdGenerator = new AtomicLong(0);

	@Override
	public Cart getCart(Long id) {
		Cart cart = cartRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		
		return cartRepository.save(cart);
	}

	@Override
	@Transactional
	public void clearCart(Long id) {
		Cart cart = getCart(id);
		cartItemRepository.deleteAllByCartId(id);
		cart.getCartItems().clear();
		cartRepository.deleteById(id);
		
	}

	@Override
	public BigDecimal getTotalAmount(Long id) {
		Cart cart = getCart(id);
		return cart.getTotalAmount();
	}
	
	@Override
	public Long initializeNewCart() {
		Cart newCart = new Cart();
		Long newCartId = cartIdGenerator.incrementAndGet();
		newCart.setId(newCartId);
		return cartRepository.save(newCart).getId();
	}
	
}



