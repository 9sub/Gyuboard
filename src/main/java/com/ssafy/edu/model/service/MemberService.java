package com.ssafy.edu.model.service;

import java.util.List;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.MemberDto;

public interface MemberService {
	MemberDto login(MemberDto memberDto);
}
