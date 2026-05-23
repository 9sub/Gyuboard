package com.ssafy.edu.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.CommentDto;
import com.ssafy.edu.model.mapper.BoardMapper;
import com.ssafy.edu.model.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService{
	
	private final CommentMapper commentmapper;
	
	@Override
	public List<CommentDto> list(int boardId){
		return commentmapper.selectByBoardId(boardId);
	}

	@Override
	public int write(CommentDto commentdto) {
		// TODO Auto-generated method stub
		return commentmapper.insert(commentdto);
	}

	@Override
	public int delete(int commentId) {
		// TODO Auto-generated method stub
		return commentmapper.delete(commentId);
	}

	@Override
	public int deleteByBoardId(int boardId) {
		// TODO Auto-generated method stub
		return commentmapper.deleteByBoardId(boardId);
	}

	@Override
	public CommentDto detail(int commentId) {
		// TODO Auto-generated method stub
		return commentmapper.selectOne(commentId);
	}

}
