package com.learn.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.learn.dto.OrderDto;
import com.learn.enums.OrderStatus;
import com.learn.exception.ResourceNotFoundException;
import com.learn.model.Cart;
import com.learn.model.Order;
import com.learn.model.OrderItem;
import com.learn.model.Product;
import com.learn.repository.OrderRepository;
import com.learn.repository.ProductRepository;
import com.learn.service.cart.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final CartService cartService;
	private final ModelMapper modelMapper;

	//Save order
	@Override
	public Order placeOrder(Long userId) {
		Cart cart = cartService.getCartByUserId(userId);
		
		if(cart.getCartItems().isEmpty()) {
			throw new IllegalStateException("Cart is empty Cannot place an order.");
		}
		
		Order order = createOrder(cart);
		List<OrderItem> orderItemList = createOrderItems(order, cart);
		
		order.setOrderItems(new HashSet<>(orderItemList));
		order.setTotalAmount(calculateTotalAmount(orderItemList));	
		Order savedOrder = orderRepository.save(order);	
		
		cartService.clearCart(cart.getId()); //by cartId
		
		return savedOrder;
	}
	
	private Order createOrder(Cart cart) {
		Order order = new Order();
		order.setUser(cart.getUser());
		order.setOrderStatus(OrderStatus.PENDING);
		order.setOrderDate(LocalDate.now());
		return order;
	}
	
	private List<OrderItem> createOrderItems(Order order, Cart cart){
		return cart.getCartItems().stream().map(cartItem -> {
			Product product = cartItem.getProduct();
			product.setInventory(product.getInventory() - cartItem.getQuantity());
			productRepository.save(product);
			return new OrderItem(
					order, 
					product, 
					cartItem.getQuantity(), 
					cartItem.getUnitPrice());
		}).toList();
	}
	
	private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
		return orderItemList
				.stream()
				.map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	//done coding from bottom to top for placeOrder

	//get order by id
	@Override
	public OrderDto getOrder(Long orderId) {
		return orderRepository.findById(orderId)
				.map(this :: convertToDto)
				.orElseThrow(() -> new ResourceNotFoundException("No order found"));
	}
	
	//get all orders by user id
	@Override
	public List<OrderDto> getUserOrders(Long userId){
		List<Order> orders = orderRepository.findByUserId(userId);
		return orders.stream().map(this :: convertToDto).toList();  
	}
	
	// Convert Order to OrderDto
	@Override
	public OrderDto convertToDto(Order order) {
		return modelMapper.map(order, OrderDto.class);
	}
	
	
}





