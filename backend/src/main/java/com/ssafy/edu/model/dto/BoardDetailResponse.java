package com.ssafy.edu.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardDetailResponse { // 댓글 리스트 묶어서 전달 

	private BoardDto board;
	private List<CommentDto> comments;
	
}
