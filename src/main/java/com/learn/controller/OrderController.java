package com.learn.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.dto.OrderDto;
import com.learn.exception.ResourceNotFoundException;
import com.learn.model.Order;
import com.learn.response.ApiResponse;
import com.learn.service.order.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}")
public class OrderController {
	
	private final OrderService orderService;
	
	
	@PostMapping("/orders")
	public ResponseEntity<ApiResponse> placeOrder(@RequestParam Long userId){
		try {
			Order order = orderService.placeOrder(userId);
			OrderDto orderDto = orderService.convertToDto(order);
			return ResponseEntity.ok(new ApiResponse("Order placed successfully!", orderDto));
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("Order failed!", "Cart is empty. Cannot place an order."));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("An unexpected error occurred!", e.getMessage()));
		} 
	}
	
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
		try {
			OrderDto order = orderService.getOrder(orderId);
			return ResponseEntity.ok(new ApiResponse("Order found", order));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No order found", e.getMessage()));
		}
	}
	
	@GetMapping("/orders/user/{userId}")
	public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId){
		try {
			List<OrderDto> userOrders = orderService.getUserOrders(userId);
			return ResponseEntity.ok(new ApiResponse("User orders retrieved", userOrders));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No orders found with this user", e.getMessage()));
		}
	}
	
}





