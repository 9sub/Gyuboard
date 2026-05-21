package com.ssafy.edu.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.MemberDto;
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
	
	//필터제작 
	

}
