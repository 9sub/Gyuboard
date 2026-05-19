package com.ssafy.edu.model.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.edu.model.dto.BoardDto;

@Mapper
public interface BoardMapper {
	List<BoardDto> selectAll(@Param("type") String type,@Param("keyword") String keyword);

	int insert(BoardDto boardDto);
	
	BoardDto selectOne(int id);
	
	int update(BoardDto boardDto);
	
	int delete(int id);
}
