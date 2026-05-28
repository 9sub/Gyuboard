package com.ssafy.edu.model.service;

import java.util.List;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.MemberDto;

public interface BoardBookmarkService {
	boolean toggle(int boardId, MemberDto loginUser);
	int count(int boardId);
	boolean isBookmarked(int boardId, int userId);
	void deleteByBoardId(int boardId);
}
