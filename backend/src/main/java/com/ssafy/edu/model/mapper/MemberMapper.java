package com.ssafy.edu.model.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.MemberDto;

@Mapper
public interface MemberMapper {
	MemberDto login(MemberDto memberDto);

	MemberDto selectByWriter(String writer);

	int joinMember(MemberDto memberdto);
	
	MemberDto selectByUserId(@Param("userId") int userId);
	
	int updateProfile(MemberDto memberdto);
	
	List<BoardDto> selectLikedBoards(@Param("userId") int userId);
	
	List<BoardDto> selectBookmarkedBoards(@Param("userId") int userId);
	
}
