package com.ssafy.edu.model.dto;

import lombok.Data;

@Data
public class MemberDto {
	private int userId;
	private String writer;
	private String name;
	private String password;
	private String email;
	private String regidate;
	
}
