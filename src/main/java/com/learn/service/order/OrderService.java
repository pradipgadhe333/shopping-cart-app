package com.learn.service.order;

import java.util.List;

import com.learn.dto.OrderDto;
import com.learn.model.Order;

public interface OrderService {
	
	Order placeOrder(Long userId);
	OrderDto getOrder(Long orderId);
	List<OrderDto> getUserOrders(Long userId);
	OrderDto convertToDto(Order order);
	
}
