package com.ssafy.edu.model.service;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ssafy.edu.exception.ApiException;
import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.MemberDto;
import com.ssafy.edu.model.mapper.BoardBookmarkMapper;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BoardBookmarkServiceImpl implements BoardBookmarkService{

	private final BoardService boardservice;
	private final BoardBookmarkMapper boardbookmarkmapper;
	
	
	@Override
	public boolean toggle(int boardId, MemberDto loginUser) {
		BoardDto board = boardservice.detail(boardId);
		
		if(board == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "BOARD_NOT_FOUND", "게시글을 찾을 수 없습니다.");
		}
		
		int exist = boardbookmarkmapper.exists(boardId, loginUser.getUserId());
		
		if(exist>0) {
			boardbookmarkmapper.delete(boardId, loginUser.getUserId());
			return false;
		}
		
		boardbookmarkmapper.insert(boardId, loginUser.getUserId());
		return true;
		
	}
	@Override
	public int count(int boardId) {
		// TODO Auto-generated method stub
		return boardbookmarkmapper.countByBoardId(boardId);
	}
	@Override
	public boolean isBookmarked(int boardId, int userId) {
		// TODO Auto-generated method stub
		return boardbookmarkmapper.exists(boardId, userId)>0;
	}
	@Override
	public void deleteByBoardId(int boardId) {
		// TODO Auto-generated method stub
		boardbookmarkmapper.deleteByBoardId(boardId);
	}

}
