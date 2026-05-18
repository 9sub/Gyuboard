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
	

}
