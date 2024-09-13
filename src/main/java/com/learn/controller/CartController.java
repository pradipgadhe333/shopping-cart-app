package com.learn.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learn.exception.ResourceNotFoundException;
import com.learn.model.Cart;
import com.learn.response.ApiResponse;
import com.learn.service.cart.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}")
public class CartController {

	private final CartService cartService;
	
	@GetMapping("/carts/{cartId}")
	public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
		try {
			Cart cart = cartService.getCart(cartId);
			return ResponseEntity.ok(new ApiResponse("Success", cart));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/carts/{cartId}/clear")
	public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
		try {
			cartService.clearCart(cartId);
			return ResponseEntity.ok(new ApiResponse("Clear Cart Success!", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	@GetMapping("/carts/{cartId}/total-price")
	public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId){
		try {
			BigDecimal totalAmount = cartService.getTotalAmount(cartId);
			return ResponseEntity.ok(new ApiResponse("Total Amount", totalAmount));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
}





