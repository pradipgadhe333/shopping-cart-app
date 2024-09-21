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
import com.learn.model.Cart;
import com.learn.model.User;
import com.learn.response.ApiResponse;
import com.learn.service.cart.CartItemService;
import com.learn.service.cart.CartService;
import com.learn.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
	
	private final CartItemService cartItemService;
	private final CartService cartService;
	private final UserService userService;
	
	
	//Add an item to a cart
	@PostMapping("/carts/items")
	public ResponseEntity<ApiResponse> addItemToCart(
													 @RequestParam Long productId, 
													 @RequestParam Integer quantity ){	
		try {
			User user = userService.getUserById(4L);
			Cart cart = cartService.initializeNewCart(user);
			
			cartItemService.addItemToCart(cart.getId(), productId, quantity);
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





