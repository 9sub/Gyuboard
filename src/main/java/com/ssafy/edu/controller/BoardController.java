package com.ssafy.edu.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.CommentDto;
import com.ssafy.edu.model.dto.MemberDto;
import com.ssafy.edu.model.dto.PageDto;
import com.ssafy.edu.model.service.BoardService;
import com.ssafy.edu.model.service.CommentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {
	
	private final BoardService boardservice;
	private final CommentService commentservice;

	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/board/list")
	public String list(String type, 
			String keyword, 
			@RequestParam(defaultValue = "1") int page,
			Model model, 
			HttpSession session) {
		
		if(session.getAttribute("user") == null)
			return "redirect:/member/login";
		
		int size=10;
		
		int totalCount = boardservice.count(type, keyword);
		PageDto pagedto = new PageDto(page, size, totalCount);
		
		List<BoardDto> list = boardservice.list(type, keyword, pagedto.getOffset(), pagedto.getSize());
		model.addAttribute("list",list);
		model.addAttribute("type",type);
		model.addAttribute("keyword",keyword);
		model.addAttribute("pageDto", pagedto);
		return "board/list";
	}
	
	@GetMapping("/board/write")
	public String writeForm(HttpSession session) {
		if(session.getAttribute("user") == null) {
			return "redirect:/member/login";
		}
		return "board/write";
	}
	
	@PostMapping("/board/write")
	public String write(BoardDto boardDto, HttpSession session) {
		MemberDto user = (MemberDto) session.getAttribute("user");
		log.info("글쓰기 요청 : {}",boardDto);
		
		if(session.getAttribute("user") == null) {
			return "redirect:/member/login";
		}
		
		boardDto.setWriter(user.getWriter());
		
		boardservice.write(boardDto);
		return "redirect:/board/list";
	}

	@GetMapping("/board/detail")
	public String detail(int id, Model model) {
		int cnt = boardservice.updateViewCount(id);
		BoardDto board = boardservice.detail(id);
		
		model.addAttribute("viewCount", cnt);
		model.addAttribute("board",board);
		model.addAttribute("comments", commentservice.list(id));
		
		return "board/detail";
	}
	
	@GetMapping("/board/detail/update")
	public String updateForm(int id, HttpSession session, Model model) {
		BoardDto board = boardservice.detail(id);
		MemberDto loginUser = (MemberDto) session.getAttribute("user");
		
		if(loginUser == null) {
			return "redirect:/login";
		}
		
		if(!board.getWriter().equals(loginUser.getWriter())) {
			return "redirect:/board/detail?id="+id;
		}
		
		model.addAttribute("board", board);
		return "board/update";
	}
	
	@PostMapping("/board/detail/update")
	public String updateComment(BoardDto boarddto, HttpSession session) {
		BoardDto origin = boardservice.detail(boarddto.getId());
		MemberDto user = (MemberDto) session.getAttribute("user");
		
		if(user == null) {
			return "redirect:/member/login";
		}
		if(!origin.getWriter().equals(user.getWriter())) {
			return "redirect:/board/detail?id="+boarddto.getId();
			
		}
		
		boardservice.update(boarddto);
		return "redirect:/board/detail?id="+boarddto.getId();
	}
	
	@PostMapping("/board/detail/write")
	public String commentWrite(CommentDto commentdto, HttpSession session) {
		MemberDto loginuser = (MemberDto) session.getAttribute("user");
		if(loginuser == null) {
			return "redirect:/member/login";
		}
		commentdto.setWriter(loginuser.getWriter());
		commentservice.write(commentdto);
		return "redirect:/board/detail?id=" + commentdto.getBoardId();
	}
	
	@PostMapping("/board/detail/delete")
	public String deleteComment(int id, HttpSession session) {
		BoardDto board = boardservice.detail(id);
		MemberDto user = (MemberDto) session.getAttribute("user");
		
		if(user == null) {
			return "redirect:/member/login";
		}
		
		if(!board.getWriter().equals(user.getWriter())) {
			return "redirect:/board/detail?id="+id;
		}
		
		boardservice.delete(id);
		return "redirect:/board/list";
	}
	
	
	
}
