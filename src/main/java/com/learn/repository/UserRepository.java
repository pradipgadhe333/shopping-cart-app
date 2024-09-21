package com.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);
	
}
