package com.ssafy.edu.model.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ssafy.edu.exception.ApiException;
import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.MemberDto;
import com.ssafy.edu.model.mapper.BoardLikeMapper;
import com.ssafy.edu.model.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardLikeServiceImpl implements BoardLikeService{
	
	private final BoardService boardservice;
	private final BoardLikeMapper boardlikemapper;
	
	
	@Override
	public boolean toggle(int boardId, MemberDto loginUser) {
		BoardDto board = boardservice.detail(boardId);
		
		if(board == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "BOARD_NOT_FOUND", "게시글을 찾을 수 없습니다.");
		}
		
		int exist = boardlikemapper.exists(boardId, loginUser.getUserId());
		
		if(exist>0) {
			boardlikemapper.delete(boardId, loginUser.getUserId());
			return false;
		}
		
		boardlikemapper.insert(boardId, loginUser.getUserId());
		return true;
		
	}
	@Override
	public int count(int boardId) {
		// TODO Auto-generated method stub
		return boardlikemapper.countByBoardId(boardId);
	}
	@Override
	public boolean isLiked(int boardId, int userId) {
		// TODO Auto-generated method stub
		return boardlikemapper.exists(boardId, userId)>0;
	}
	@Override
	public void deleteByBoardId(int boardId) {
		// TODO Auto-generated method stub
		boardlikemapper.deleteByBoardId(boardId);
	}


}
