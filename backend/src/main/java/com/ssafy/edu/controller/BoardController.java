package com.ssafy.edu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.exception.ApiException;
import com.ssafy.edu.model.dto.BoardDetailResponse;
import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.BoardListResponse;
import com.ssafy.edu.model.dto.CommentDto;
import com.ssafy.edu.model.dto.MemberDto;
import com.ssafy.edu.model.dto.PageDto;
import com.ssafy.edu.model.service.BoardService;
import com.ssafy.edu.model.service.CommentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
	
	private final BoardService boardservice;
	private final CommentService commentservice;

	@GetMapping
	public ResponseEntity<BoardListResponse> list(
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String keyword,
			@RequestParam(defaultValue =  "1") int page,
			@RequestParam(defaultValue = "latest") String sort
			
			){
		int size=10;
		int totalCnt = boardservice.count(type, keyword);
		PageDto pagedto = new PageDto(page, size, totalCnt);
		
		List<BoardDto> list = boardservice.list(type, 
				keyword, 
				sort, 
				pagedto.getOffset(), 
				pagedto.getSize());
		
		BoardListResponse response = new BoardListResponse(list, 
				pagedto, 
				type, 
				keyword, 
				sort);
		
//		log.info("REST 게시글 목록 조회: writer={}, userId={}", loginUser.getWriter(), loginUser.getUserId());
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<Void> wrtie(
				@RequestBody BoardDto boarddto,
				@RequestAttribute("loginUser") MemberDto loginUser
			){
		boarddto.setUserId(loginUser.getUserId());
		
		boardservice.write(boarddto);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BoardDetailResponse> detail(
				@PathVariable int id
			){
		 
		boardservice.updateViewCount(id);
		
		BoardDto boarddto = boardservice.detail(id);
		
		List<CommentDto> comments = commentservice.list(id);
		
		log.info("heloo-------------------------------------------------");  
		log.info("comments = {}",comments.toString());
		
		BoardDetailResponse response = new BoardDetailResponse(boarddto, comments);
		
		return ResponseEntity.ok(response);
	}
	
	
	
}
