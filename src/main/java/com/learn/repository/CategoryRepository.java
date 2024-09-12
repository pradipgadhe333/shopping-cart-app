package com.learn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Category findByName(String name);

	boolean existsByName(String name);

}
