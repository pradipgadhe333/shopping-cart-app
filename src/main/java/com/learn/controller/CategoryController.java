package com.learn.controller;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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
import org.springframework.web.bind.annotation.RestController;

import com.learn.exception.AlreadyExistsException;
import com.learn.exception.ResourceNotFoundException;
import com.learn.model.Category;
import com.learn.response.ApiResponse;
import com.learn.service.category.CategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	// Get all categories
	@GetMapping("/categories")
	public ResponseEntity<ApiResponse> getAllCategories(){
		try {
			List<Category> categories = categoryService.getAllCategories();
			return ResponseEntity.ok(new ApiResponse("Found categories!", categories));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error retrieving categories!", INTERNAL_SERVER_ERROR));
		}
	}
	
	// Add a new category
	@PostMapping("/categories")
	public ResponseEntity<ApiResponse> addCategory(@RequestBody Category category){
		try {
			Category theCategory = categoryService.addCategory(category);
			return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("Category added successfully!", theCategory));
		} catch (AlreadyExistsException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	// Get a category by ID
	@GetMapping("/categories/{id}")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
		try {
			Category theCategory = categoryService.getCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("Category found!", theCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	// Get a category by name
	@GetMapping("/categories/name/{name}")
	public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
		try {
			Category theCategory = categoryService.getCategoryByName(name);
			return ResponseEntity.ok(new ApiResponse("Category found!", theCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	// Delete a category by ID
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id){
		try {
			categoryService.deleteCategoryById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	// Update a category by ID
	@PutMapping("/categories/{id}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category){
		try {
			Category updatedCategory = categoryService.updateCategory(category, id);
			return ResponseEntity.ok(new ApiResponse("Category updated successfully!", updatedCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
}




