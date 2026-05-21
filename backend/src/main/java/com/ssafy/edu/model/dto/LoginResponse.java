package com.ssafy.edu.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse{
	private String token;
	private MemberDto user;
}