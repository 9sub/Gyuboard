package com.ssafy.edu.model.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.CommentDto;

@Mapper
public interface CommentMapper {
	List<CommentDto> selectByBoardId(@Param("boardId") int boardId);
	
	CommentDto selectOne(@Param("commentId") int commentId);
	
	int insert(CommentDto commentdto);
	
	int delete(@Param("commentId") int commentId);
	
	int deleteByBoardId(@Param("boardId") int boardId);
}
