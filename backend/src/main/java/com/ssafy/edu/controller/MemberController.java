package com.ssafy.edu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.exception.ApiException;
import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.LoginRequest;
import com.ssafy.edu.model.dto.LoginResponse;
import com.ssafy.edu.model.dto.MemberDto;
import com.ssafy.edu.model.service.BoardService;
import com.ssafy.edu.model.service.MemberService;
import com.ssafy.edu.util.JwtUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberservice;
	private final JwtUtil jwtutil;
	
	
	private boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
	
	
	
	@PostMapping("/api/member/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
		String writer = request.getWriter();
		
		if(writer == null || isBlank(writer)) {
			writer = request.getUserId();
		}
		
		MemberDto loginParam = new MemberDto();
		loginParam.setWriter(writer);
		loginParam.setPassword(request.getPassword());
		
		MemberDto user = memberservice.login(loginParam);
		
		if(user == null) {
			throw new ApiException(HttpStatus.UNAUTHORIZED, "LOGIN_FAILED", "아이디 또는 비밀번호가 올바르지 않습니다.");
		}
		
		String token = jwtutil.createToken(user);
		
		log.info("REST 로그인 성공: writer={}, userId{}", user.getWriter(), user.getUserId());
		
		LoginResponse response = new LoginResponse(token, user);
		
		return ResponseEntity.ok(response);
	}
	
	
	
	
}
