package com.ssafy.edu.model.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.edu.exception.ApiException;


@Service
public class ImageUploadServiceImpl implements ImageUploadService{

	private static final String BOARD_UPLOAD_DIR = "uploads/board";

    private static final String BOARD_IMAGE_URL_PREFIX = "/uploads/board/";
	
    @Override
	public String uploadBoardImage(MultipartFile image) throws IOException{
		if(image == null || image.isEmpty()) return null;
		
		validateImage(image);
		
		Path uploadPath = Paths.get(BOARD_UPLOAD_DIR)
				.toAbsolutePath()
				.normalize();
		
		Files.createDirectories(uploadPath);
		
		String savedFilename = createSavedFilename(image.getOriginalFilename());
		
		Path targetPath = uploadPath.resolve(savedFilename).normalize();
		
		Files.copy(
					image.getInputStream(),
					targetPath,
					StandardCopyOption.REPLACE_EXISTING
				);
		return BOARD_IMAGE_URL_PREFIX+savedFilename;
	}
    
    private void validateImage(MultipartFile image) {
    	String contentType = image.getContentType();
    	
    	if(contentType == null || !contentType.startsWith("image/")) {
    		throw new ApiException(
    				HttpStatus.BAD_REQUEST, 
    				"INVALID_FILE_TYPE",
    				"이미지 파일만 업로드할 수 있습니다.");
    		
    	}
    }
    
    
    private String createSavedFilename(String originalFilename) {
    	String extension = "";
    	if(originalFilename != null && originalFilename.contains(".")) {
    		extension = originalFilename.substring(originalFilename.lastIndexOf("."));
    	}
    	return UUID.randomUUID().toString() + extension;
    }
    
}
