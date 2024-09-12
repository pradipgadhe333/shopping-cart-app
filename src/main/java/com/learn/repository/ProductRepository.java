package com.learn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>  {

	//Custom Finder Methods or JPA Query Methods
	
	List<Product> findByCategoryName(String category);

	List<Product> findByBrand(String brand);

	List<Product> findByCategoryNameAndBrand(String category, String brand); 
	List<Product> findByName(String name);

	List<Product> findByBrandAndName(String brand, String name);

	Long countByBrandAndName(String brand, String name);
	
	

}



