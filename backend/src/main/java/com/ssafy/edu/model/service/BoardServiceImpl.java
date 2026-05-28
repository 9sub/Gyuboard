package com.ssafy.edu.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{
	
	private final BoardMapper boardMapper;

	@Override
	public List<BoardDto> list(String type, String keyword, String sort, int offset, int size){
		return boardMapper.selectAll(type, keyword, sort, offset, size);
	}

	@Override
	public int write(BoardDto boardDto) {
		// TODO Auto-generated method stub
		log.info("write function in boardserviceimpl  2");
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

	@Override
	public int count(String type, String keyword) {
		
		return boardMapper.count(type, keyword);
	}

	@Override
	public int updateViewCount(int id) {
		// TODO Auto-generated method stub
		return boardMapper.updateViewCount(id);
	}
	

}
