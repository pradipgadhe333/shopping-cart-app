package com.learn.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.exception.ResourceNotFoundException;
import com.learn.response.ApiResponse;
import com.learn.service.cart.CartItemService;
import com.learn.service.cart.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
	
	private final CartItemService cartItemService;
	private final CartService cartService;
	
	//Add an item to a cart
	@PostMapping("/carts/{cartId}/items")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId, 
													 @RequestParam Long productId, 
													 @RequestParam Integer quantity ){	
		try {
			
			if(cartId == null) {
				cartId = cartService.initializeNewCart();
			}
			
			cartItemService.addItemToCart(cartId, productId, quantity);
			return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	//Remove an item from a cart
	@DeleteMapping("/carts/{cartId}/items/{itemId}")
	public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId){
		try {
			cartItemService.removeItemFromCart(cartId, itemId);
			return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	//Update the quantity of an item in the cart
	@PutMapping("/carts/{cartId}/items/{itemId}")
	public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId, 
														  @PathVariable Long itemId, 
														  @RequestParam Integer quantity){
		try {
			cartItemService.updateItemQuantity(cartId, itemId, quantity);
			return ResponseEntity.ok(new ApiResponse("Update Item Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
}





