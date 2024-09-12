package com.learn.service.product;

import java.util.List;

import com.learn.dto.ProductDto;
import com.learn.model.Product;
import com.learn.request.AddProductRequest;
import com.learn.request.ProductUpdateRequest;

public interface ProductService {
	
	Product addProduct(AddProductRequest product);
	Product getProductById(Long id);
	void deleteProductById(Long id);
	Product updateProduct(ProductUpdateRequest product, Long productId);
	
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category, String brand);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String brand, String name);
	Long countProductsByBrandAndName(String brand, String name);
	
	ProductDto convertToDto(Product product);
	List<ProductDto> getConvertedProducts(List<Product> products);
	
}





