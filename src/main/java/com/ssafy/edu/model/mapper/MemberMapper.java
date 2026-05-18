package com.ssafy.edu.model.mapper;


import org.apache.ibatis.annotations.Mapper;


import com.ssafy.edu.model.dto.MemberDto;

@Mapper
public interface MemberMapper {
	MemberDto login(MemberDto memberDto);
}
