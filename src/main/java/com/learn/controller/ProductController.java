package com.learn.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.dto.ProductDto;
import com.learn.exception.AlreadyExistsException;
import com.learn.exception.ResourceNotFoundException;
import com.learn.model.Product;
import com.learn.request.AddProductRequest;
import com.learn.request.ProductUpdateRequest;
import com.learn.response.ApiResponse;
import com.learn.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}")
public class ProductController {
	
	private final ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<ApiResponse> getAllProducts(){
		List<Product> products = productService.getAllProducts();
		List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
		return ResponseEntity.ok(new ApiResponse("Products retrieved successfully!", convertedProducts));
	}
	
	@GetMapping("/products/{productId}")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
		try {
			Product product = productService.getProductById(productId);
			ProductDto productDto = productService.convertToDto(product);
			return ResponseEntity.ok(new ApiResponse("Product retrieved successfully!", productDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Product not found!", null));
		}
	}
	
	@PostMapping("/products")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
		try {
			Product theProduct = productService.addProduct(product);
			ProductDto productDto = productService.convertToDto(theProduct);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Product added successfully!", productDto));
		} catch (AlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/products/{productId}")
	public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest product, @PathVariable Long productId){
		try {
			Product updatedProduct = productService.updateProduct(product, productId);
			ProductDto productDto = productService.convertToDto(updatedProduct);
			return ResponseEntity.ok(new ApiResponse("Product updated successfully!", productDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
		try {
			productService.deleteProductById(productId);
			return ResponseEntity.ok(new ApiResponse("Product deleted successfully!", productId));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/products/search/by-brand-and-name")
	public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
		try {
			List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Products retrieved successfully!", convertedProducts));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occurred while retrieving products", null));
		}
	}
	
	@GetMapping("/products/search/by-category-and-brand")
	public ResponseEntity<ApiResponse> getProductByCategoryAndName(@RequestParam String categoryName, @RequestParam String brandName){
		try {
			List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Products retrieved successfully!", convertedProducts));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occurred while retrieving products", null));
		}
	}
	
	@GetMapping("/products/search/by-name/{name}")
	public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
		try {
			List<Product> products = productService.getProductsByName(name);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Products retrieved successfully!", convertedProducts));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occurred while retrieving products", null));
		}
	}
	
	@GetMapping("/products/search/by-brand")
	public ResponseEntity<ApiResponse> findProductByBrand(@RequestParam String brand){
		try {
			List<Product> products = productService.getProductsByBrand(brand);			
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Products retrieved successfully!", convertedProducts));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occurred while retrieving products", null));
		}
	}
	
	@GetMapping("/products/search/by-category/{category}")
	public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category){
		try {
			List<Product> products = productService.getProductsByCategory(category);
			if(products.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No products found!", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Products retrieved successfully!", convertedProducts));
		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occurred while retrieving products", null));
		}
	}
	
	@GetMapping("/products/count/by-brand-and-name")
	public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name){
		try {
			Long productCount = productService.countProductsByBrandAndName(brand, name);
			return ResponseEntity.ok(new ApiResponse("Product count retrieved successfully!", productCount));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("An error occurred while counting products", null));
		}
	}
	
}






