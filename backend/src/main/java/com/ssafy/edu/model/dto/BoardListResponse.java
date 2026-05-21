package com.ssafy.edu.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardListResponse {// 게시글 묶어서 전달

	
	private List<BoardDto> list;
	private PageDto pageDto;
	private String type;
	private String keyword;
	private String sort;
}
