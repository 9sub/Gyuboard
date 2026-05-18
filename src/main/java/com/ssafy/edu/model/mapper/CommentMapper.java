package com.ssafy.edu.model.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.CommentDto;

@Mapper
public interface CommentMapper {
	List<CommentDto> selectByBoardId(@Param("boardId") int boardId);
	
	int insert(CommentDto commentdto);
	
	int delete(@Param("commentID") int commentId);
}
