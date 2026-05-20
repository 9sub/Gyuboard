package com.ssafy.edu.model.dto;

import java.time.LocalDateTime;

import lombok.Data;


@Data
public class BoardDto {
	
	private int id;
	private String writer;
	private String title;
	private String guecontents;
	private LocalDateTime writedate;
	private int viewCount;
}
