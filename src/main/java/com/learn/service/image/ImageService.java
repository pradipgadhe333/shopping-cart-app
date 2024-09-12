package com.learn.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.learn.dto.ImageDto;
import com.learn.model.Image;

public interface ImageService {
	
	Image getImageById(Long id);
	void deleteImageById(Long id);
	List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
	void updateImage(MultipartFile file, Long imageId);
	
}
