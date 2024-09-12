package com.learn.service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.dto.ImageDto;
import com.learn.dto.ProductDto;
import com.learn.exception.ResourceNotFoundException;
import com.learn.model.Category;
import com.learn.model.Image;
import com.learn.model.Product;
import com.learn.repository.CategoryRepository;
import com.learn.repository.ImageRepository;
import com.learn.repository.ProductRepository;
import com.learn.request.AddProductRequest;
import com.learn.request.ProductUpdateRequest;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ImageRepository imageRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Product addProduct(AddProductRequest request) {
		// check if the category found in the DB
		// If Yes, set it as the new product category
		// If No, then save it as a new category
		// Then set as the new product category.
		
		Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
				.orElseGet(()->{
					Category newCategory = new Category(request.getCategory().getName());
					return categoryRepository.save(newCategory);
				});
		request.setCategory(category);
		return productRepository.save(createProduct(request, category));
	}
	
	private Product createProduct(AddProductRequest request, Category category) {
		return new Product(
				request.getName(),
				request.getBrand(),
				request.getPrice(),
				request.getInventory(),
				request.getDescription(),
				category
		);
	}
	

	@Override
	public Product getProductById(Long id) {
		Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found!"));
		return product;
	}

	@Override
	public void deleteProductById(Long id) {
		productRepository.findById(id)
		.ifPresentOrElse(productRepository :: delete, () -> {throw new ResourceNotFoundException("Product not found!");});
		
	}

	@Override
	public Product updateProduct(ProductUpdateRequest request, Long productId) {
		return productRepository.findById(productId)
				.map(existingProduct -> updateExistingProduct(existingProduct, request))
				.map(productRepository :: save)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
	}
	
	private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		existingProduct.setPrice(request.getPrice()); 
		existingProduct.setInventory(request.getInventory());
		existingProduct.setDescription(request.getDescription());
		
		Category category = categoryRepository.findByName(request.getCategory().getName());
		existingProduct.setCategory(category);
		
		return existingProduct;
		
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		return productRepository.findByCategoryNameAndBrand(category, brand);
	}

	@Override
	public List<Product> getProductsByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductsByBrandAndName(String brand, String name) {
		return productRepository.findByBrandAndName(brand, name);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand, name);
	}
	
	
	@Override
	public List<ProductDto> getConvertedProducts(List<Product> products){
		return products.stream().map(this::convertToDto).toList();
	}
	
	@Override
	public ProductDto convertToDto(Product product){
		ProductDto productDto = modelMapper.map(product, ProductDto.class);
		List<Image> images = imageRepository.findByProductId(product.getId());
		List<ImageDto> imageDtos = images.stream()
				.map(image -> modelMapper.map(image, ImageDto.class))
				.toList();		
		productDto.setImages(imageDtos);
		return productDto;
	}

}




