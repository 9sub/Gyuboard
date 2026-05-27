package com.ssafy.edu.model.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.MemberDto;
import com.ssafy.edu.model.dto.MemberUpdateRequest;

public interface MemberService {
	MemberDto login(MemberDto memberDto);
	MemberDto findByWriter(String writer);
	int joinMember(MemberDto memberdto);
	
	MemberDto findByUserId(int userId);
	
	MemberDto updateProfile(int userId, MemberUpdateRequest request);
	
	List<BoardDto> LikedBoards(int userId);
	
	List<BoardDto> bookmarkedBoards(int userId);
	
	
	
}
