package com.ssafy.edu.model.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.CommentDto;

public interface CommentService {
	List<CommentDto> list(int boardId);
	
	CommentDto detail(int commentId);
	
	int write(CommentDto commentdto);
	int delete(int commentId);
	
	int deleteByBoardId(@Param("boardId") int boardId);
}
