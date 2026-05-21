package com.ssafy.edu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


	private MemberDto getLoginUser(HttpSession session) {
		MemberDto user = (MemberDto) session.getAttribute("user");
		
		if(user == null) {
			throw new ApiException(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "로그인이 필요합니다.");
		}
		return user;
	}
	
	@GetMapping
	public ResponseEntity<BoardListResponse> list(
				@RequestParam(required = false) String type,
				@RequestParam(required = false) String keyword,
				@RequestParam(defaultValue = "1") int page,
				@RequestParam(defaultValue = "latest") String sort,
				HttpSession session
			){
		
		
		getLoginUser(session);
		
		int size=10;
		int totalCount = boardservice.count(type, keyword);
		PageDto pagedto = new PageDto(page, size, totalCount);
		
		List<BoardDto> list = boardservice.list(type, keyword, sort, pagedto.getOffset(), pagedto.getSize());
		
		BoardListResponse response = new BoardListResponse(list, pagedto, type, keyword, sort);
		
		log.info("response entity list 에러 표시 {}",response);
		
		return ResponseEntity.ok(response);
	}
	
	
	@PostMapping
	public ResponseEntity<Void> write(
				@RequestBody BoardDto boarddto,
				HttpSession session
			){
		
		MemberDto user = getLoginUser(session);
		
		boarddto.setUserId(user.getUserId());
		boardservice.write(boarddto);
		
		log.info("REST 게시글 작성 완료: writer={}, userId={}",
                user.getWriter(),
                user.getUserId()
        );
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<BoardDetailResponse> detail(
				@PathVariable int id,
				HttpSession session
			){
		
		getLoginUser(session);
		
		boardservice.updateViewCount(id);
		
		BoardDto board = boardservice.detail(id);
		
		if(board == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "BOARD_NOT_FOUND", "게시글을 찾을 수 없습니다.");
		}
		
		List<CommentDto> comments = commentservice.list(id);
		
		BoardDetailResponse response = new BoardDetailResponse(board, comments);
		
		return ResponseEntity.ok(response);
		
	}
	
//	
//	@GetMapping("/{id}")
//	public ResponseEntity<BoardDto> update(
//				@PathVariable int id,
//				@RequestBody BoardDto boarddto,
//				HttpSession session
//			){
//		
//		MemberDto user = getLoginUser(session);
//		
//		BoardDto board = boardservice.detail(id);
//		
//		if(board == null) {
//			throw new ApiException(HttpStatus.NOT_FOUND, "BOARD_NOT_FOUND", "게시글을 찾을 수 없습니다.");
//		}
//		
//		if(user.getUserId() != board.getUserId()) {
//			throw new ApiException(HttpStatus.FORBIDDEN, "FORBIDDEN", "수정 권한이 없습니다.");
//		}
//		
//		boarddto.setId(id);
//		boarddto.setUserId(user.getUserId());
//		
//		boardservice.update(boarddto);
//		
//	}
	
	
	
}
