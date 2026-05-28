package com.ssafy.edu.model.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

	String uploadBoardImage(MultipartFile image) throws IOException;
}
