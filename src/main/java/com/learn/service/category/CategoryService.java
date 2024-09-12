package com.learn.service.category;

import java.util.List;

import com.learn.model.Category;

public interface CategoryService {
	
	Category addCategory(Category category);
	Category updateCategory(Category category, Long id);
	void deleteCategoryById(Long id);
	
	Category getCategoryById(Long id);
	Category getCategoryByName(String name);
	List<Category> getAllCategories();

}
