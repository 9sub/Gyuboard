package com.ssafy.edu.model.service;

import java.util.List;

import com.ssafy.edu.model.dto.BoardDto;

public interface BoardService {
	List<BoardDto> list(String type, String keyword, String sort, int offset, int size);
	int write(BoardDto boardDto);
	BoardDto detail(int id);
	int update(BoardDto boardDto);
	int delete(int id);
	int count(String type, String keyword);
	int updateViewCount(int id);
}
