package com.ssafy.edu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.exception.ApiException;
import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.LoginRequest;
import com.ssafy.edu.model.dto.LoginResponse;
import com.ssafy.edu.model.dto.MemberDto;
import com.ssafy.edu.model.dto.MemberUpdateRequest;
import com.ssafy.edu.model.service.BoardService;
import com.ssafy.edu.model.service.MemberService;
import com.ssafy.edu.util.JwtUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
	
	private final MemberService memberservice;
	private final JwtUtil jwtutil;
	
	
	private boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
	
	
	
	@PostMapping("/member/login")
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
		
		
		LoginResponse response = new LoginResponse(token, user);
		
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/member/logout")
	public ResponseEntity<Void> logout(){
		return ResponseEntity.noContent().build();
	}
	
	
	@PostMapping("/member/join")
	public ResponseEntity<Void> join(@RequestBody MemberDto memberdto){
		if(memberdto.getWriter() == null || memberdto.getWriter().isBlank() 
				|| memberdto.getPassword()==null || memberdto.getPassword().isBlank()
				|| memberdto.getName() == null || memberdto.getName().isBlank()) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_JOIN_REQUEST", "아이디 비밀번호 이름은 필수입니다.");
		}
		
		MemberDto user = memberservice.findByWriter(memberdto.getWriter());
		
		if(user != null) {
			throw new ApiException(HttpStatus.CONFLICT, "DUPLICATED_WRITER", "이미 존재하는 아이디입니다.");
		}
		memberservice.joinMember(memberdto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
//	@GetMapping("/me")
//	public ResponseEntity<MemberDto> me(@RequestAttribute("loginUser") MemberDto loginUser){
//		return ResponseEntity.ok(loginUser);
//	}
	
	@GetMapping("/member/me")
	public ResponseEntity<MemberDto> me(
				@RequestAttribute("loginUser") MemberDto loginUser
			){
		MemberDto member = memberservice.findByUserId(loginUser.getUserId());
		
		if(member == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "MEMBER_NOT_FOUND", "회원 정보를 찾을 수 없습니다.");
		}
		
		member.setPassword(null);
		return ResponseEntity.ok(member);
	}
	
	@PutMapping("/member/me")
	public ResponseEntity<MemberDto> updateMe(
				@RequestBody MemberUpdateRequest request,
				@RequestAttribute("loginUser") MemberDto loginUser
			){
		MemberDto updateMember = memberservice.updateProfile(loginUser.getUserId(), request);

		updateMember.setPassword(null);
		return ResponseEntity.ok(updateMember);
	}
	
	@GetMapping("/member/me/likes")
	public ResponseEntity<List<BoardDto>> likedBoards(
				@RequestAttribute("loginUser") MemberDto loginUser
			){
		List<BoardDto> list = memberservice.LikedBoards(loginUser.getUserId());
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/member/me/bookmarks")
	public ResponseEntity<List<BoardDto>> bookmarkedBoards(
				@RequestAttribute("loginUser") MemberDto loginUser
			){
		List<BoardDto> list = memberservice.bookmarkedBoards(loginUser.getUserId());
		return ResponseEntity.ok(list);
	}
	
	
}
