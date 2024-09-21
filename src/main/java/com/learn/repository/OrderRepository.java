package com.learn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByUserId(Long userId);
	
}
