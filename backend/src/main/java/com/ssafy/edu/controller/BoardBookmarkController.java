package com.ssafy.edu.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.exception.ApiException;
import com.ssafy.edu.model.dto.BoardBookmarkResponse;
import com.ssafy.edu.model.dto.BoardDetailResponse;
import com.ssafy.edu.model.dto.BoardDto;
import com.ssafy.edu.model.dto.BoardLikeResponse;
import com.ssafy.edu.model.dto.BoardListResponse;
import com.ssafy.edu.model.dto.CommentDto;
import com.ssafy.edu.model.dto.MemberDto;
import com.ssafy.edu.model.dto.PageDto;
import com.ssafy.edu.model.service.BoardBookmarkService;
import com.ssafy.edu.model.service.BoardLikeService;
import com.ssafy.edu.model.service.BoardService;
import com.ssafy.edu.model.service.CommentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardBookmarkController {
	
	private final BoardBookmarkService boardbookmarkservice;
	
	@PostMapping("/{boardId}/bookmark")
	public ResponseEntity<BoardBookmarkResponse> toggleLike(
				@PathVariable int boardId,
				@RequestAttribute("loginUser") MemberDto loginUser
			){
		
		boolean bookmarked = boardbookmarkservice.toggle(boardId, loginUser);
		int bookmarkedCount = boardbookmarkservice.count(boardId);
		
		log.info("book mark ={} count = {}", bookmarked, bookmarkedCount);
		
		return ResponseEntity.ok(new BoardBookmarkResponse(bookmarked, bookmarkedCount));
	}
	

	
}
