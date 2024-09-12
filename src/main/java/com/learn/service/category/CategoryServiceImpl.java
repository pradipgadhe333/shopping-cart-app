package com.learn.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learn.exception.AlreadyExistsException;
import com.learn.exception.ResourceNotFoundException;
import com.learn.model.Category;
import com.learn.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;
	
	@Override
	public Category addCategory(Category category) {
		return Optional.of(category)
				.filter(c -> !categoryRepository.existsByName(c.getName()))  // Check if the category does not already exist //return true only if a category with that name does not exist //If the category already exists, filter will result in an empty Optional, causing the orElseThrow to be executed.
				.map(categoryRepository :: save)  // Save and return the new category if it doesn't exist
				.orElseThrow(() -> new AlreadyExistsException(category.getName() + " category already exists"));
	}

	@Override
	public Category updateCategory(Category category, Long id) {
		return Optional.ofNullable(getCategoryById(id)).map(oldCategory -> {
			oldCategory.setName(category.getName());
			return categoryRepository.save(oldCategory);
		}).orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
	}

	@Override
	public void deleteCategoryById(Long id) {
		categoryRepository.findById(id)
		.ifPresentOrElse(categoryRepository :: delete, () -> {
			throw new ResourceNotFoundException("Category not found!");
		});
		
	}

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Category not found!"));
	}

	@Override
	public Category getCategoryByName(String name) {
		return categoryRepository.findByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
}

