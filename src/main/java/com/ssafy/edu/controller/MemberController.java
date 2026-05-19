package com.ssafy.edu.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.MemberDto;
import com.ssafy.edu.model.service.BoardService;
import com.ssafy.edu.model.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberservice;
	
	@GetMapping("/member/login")
	public String loginForm() {
		return "/member/login";
	}
	
	@PostMapping("/member/login")
	public String login(MemberDto memberdto, HttpSession session, Model model) {
		log.info("입력된 로그인 정보:{}", memberdto);
		MemberDto user = memberservice.login(memberdto);
		log.info("db 에서 조회된로그인 유저 :{}", user);
		if(user == null) {
			model.addAttribute("msg", "아이디 또는 비밀번호가 틀렸습니다.");
			return "member/login";
		}
		session.setAttribute("user", user);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/member/join")
	public String joinForm() {
		return "member/join";
	}
	
	@PostMapping("/member/join")
	public String join(MemberDto memberdto, Model model) {
		log.info("회원가입 요청: {}", memberdto);
		
		if(memberdto.getWriter() == null || memberdto.getWriter().isEmpty()
				|| memberdto.getPassword() == null || memberdto.getPassword().isEmpty()
				|| memberdto.getName() == null || memberdto.getPassword().isEmpty()) {
			model.addAttribute("msg","아이디 비번 필수");
			return "member/join";
		}
		
		MemberDto user = memberservice.findByWriter(memberdto.getWriter());
		
		if(user != null) {
			model.addAttribute("alert", "해당 유저가 이미 존재합니다.");
			return "member/login";
		}
		
		memberservice.joinMember(memberdto);
		model.addAttribute("msg","회원가입 완료");
		return "member/login";
		
	}
	
}
