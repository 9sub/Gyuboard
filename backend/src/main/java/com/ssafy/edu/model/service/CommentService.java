package com.ssafy.edu.model.service;

import java.util.List;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.CommentDto;

public interface CommentService {
	List<CommentDto> list(int boardId);
	
	int write(CommentDto commentdto);
	int delete(int commentId);
}
