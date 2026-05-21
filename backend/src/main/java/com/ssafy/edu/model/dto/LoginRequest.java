package com.ssafy.edu.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	private String writer;
	private String userId;
	private String password;
}
