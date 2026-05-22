package com.ssafy.edu.model.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.edu.model.dto.BoardDto;

@Mapper
public interface BoardMapper {
	List<BoardDto> selectAll(@Param("type") String type, 
			@Param("keyword") String keyword, 
			@Param("sort") String sort,
			@Param("offset")int offset, 
			@Param("size") int size);

	int insert(BoardDto boardDto);
	
	BoardDto selectOne(int id);
	
	int update(BoardDto boardDto);
	
	int delete(@Param("id") int id);

	int count(@Param("type") String type,@Param("keyword") String keyword);

	int updateViewCount(int id);
}
