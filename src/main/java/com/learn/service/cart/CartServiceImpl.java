package com.learn.service.cart;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.learn.dto.CartDto;
import com.learn.exception.ResourceNotFoundException;
import com.learn.model.Cart;
import com.learn.model.User;
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
	private final ModelMapper modelMapper;
	private final AtomicLong cartIdGenerator = new AtomicLong(0);

	@Override
	public Cart getCart(Long id) {
		Cart cart = cartRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
		BigDecimal totalAmount = cart.getTotalAmount();
		cart.setTotalAmount(totalAmount);
		
		return cartRepository.save(cart);
	}

	@Transactional
	@Override
	public void clearCart(Long id) {
	    Cart cart = getCart(id);
	    cartItemRepository.deleteAllByCartId(id);
	    cart.clearCart();
	    cartRepository.deleteById(id);  // Delete the cart itself
	    
	}


	@Override
	public BigDecimal getTotalAmount(Long id) {
		Cart cart = getCart(id);
		return cart.getTotalAmount();
	}
	
	@Override
	public Cart initializeNewCart(User user) {
		return Optional.ofNullable(getCartByUserId(user.getId()))
				.orElseGet(() -> {
					Cart cart = new Cart();
					cart.setUser(user);
					return cartRepository.save(cart);
				});
	}

	@Override
	public Cart getCartByUserId(Long userId) {
		return cartRepository.findByUserId(userId);
	}
	
	@Override
	public CartDto convertToDto(Cart cart) {
		return modelMapper.map(cart, CartDto.class);
	}
	
}



