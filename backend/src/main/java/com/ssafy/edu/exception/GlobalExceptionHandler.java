package com.ssafy.edu.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErrorResponse> handleApiException(ApiException e){
		ErrorResponse response = new ErrorResponse(
					e.getCode(),
					e.getMessage()
				);
				
		
		return ResponseEntity.status(e.getStatus()).body(response);
	}
	
}
