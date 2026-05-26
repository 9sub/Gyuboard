package com.ssafy.edu.model.mapper;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.edu.model.dto.BoardDto;

@Mapper
public interface BoardLikeMapper {
	int countByBoardId(@Param("boardId") int boardId);
	
	int exists(
				@Param("boardId") int boardId,
				@Param("userId") int userId
			);
	
	int insert(
				@Param("boardId") int boardId,
				@Param("userId") int userId
			);
	
	int delete(
			@Param("boardId") int boardId,
			@Param("userId") int userId
		);
	
	int deleteByBoardId(@Param("boardId") int boardId);
	
}
