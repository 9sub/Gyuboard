package com.ssafy.edu.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentDto {
	private int commentId;
	private int boardId;
	private int userId;
	private String writer;
	private String name;
	private String content;
	private LocalDateTime writedate;
}
