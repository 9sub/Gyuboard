package com.ssafy.edu.model.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ssafy.edu.exception.ApiException;
import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.MemberDto;
import com.ssafy.edu.model.dto.MemberUpdateRequest;
import com.ssafy.edu.model.mapper.BoardMapper;
import com.ssafy.edu.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	
	private final MemberMapper membermapper;
	
	@Override
	public MemberDto login(MemberDto memberdto) {
		return membermapper.login(memberdto);
	}

	@Override
	public MemberDto findByWriter(String writer) {
		// TODO Auto-generated method stub
		return membermapper.selectByWriter(writer);
	}

	@Override
	public int joinMember(MemberDto memberdto) {
		// TODO Auto-generated method stub
		return membermapper.joinMember(memberdto);
	}

	@Override
	public MemberDto findByUserId(int userId) {
		// TODO Auto-generated method stub
		return membermapper.selectByUserId(userId);
	}

	@Override
	public MemberDto updateProfile(int userId, MemberUpdateRequest request) {
		// TODO Auto-generated method stub
		
		MemberDto member = membermapper.selectByUserId(userId);
		
		if(member == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "MEMBER_NOT_FOUND", "회원 정보를 찾을 수 없습니다.");
		}
		
		if(request.getName() == null) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_NAME", "이름을 입력해주세요.");
		}
		
		member.setName(request.getName());
		member.setEmail(request.getEmail());
		
		membermapper.updateProfile(member);
		
		return membermapper.selectByUserId(userId);
	}

	@Override
	public List<BoardDto> LikedBoards(int userId) {
		// TODO Auto-generated method stub
		return membermapper.selectLikedBoards(userId);
	}

	@Override
	public List<BoardDto> bookmarkedBoards(int userId) {
		// TODO Auto-generated method stub
		return membermapper.selectBookmarkedBoards(userId);
	}
	

}
