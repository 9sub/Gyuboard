package com.ssafy.edu.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardMapper boardMapper;

	@Override
	public List<BoardDto> list(){
		return boardMapper.selectAll();
	}

	@Override
	public int write(BoardDto boardDto) {
		// TODO Auto-generated method stub
		
		return boardMapper.insert(boardDto);
	}

	@Override
	public BoardDto detail(int id) {
		// TODO Auto-generated method stub
		return boardMapper.selectOne(id);
	}

	@Override
	public int update(BoardDto boardDto) {
		// TODO Auto-generated method stub
		return boardMapper.update(boardDto);
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return boardMapper.delete(id);
	}

}
