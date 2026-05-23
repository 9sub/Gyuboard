package com.ssafy.edu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.edu.exception.ApiException;
import com.ssafy.edu.model.dto.CommentDto;
import com.ssafy.edu.model.dto.MemberDto;
import com.ssafy.edu.model.service.BoardService;
import com.ssafy.edu.model.service.CommentService;
import com.ssafy.edu.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class CommentController {

	private final BoardService boardservice;
	private final CommentService commentservice;
	
	@PostMapping("/boards/{boardId}/comments")
	public ResponseEntity<Void> write(
				@PathVariable int boardId,
				@RequestBody CommentDto commentdto,
				@RequestAttribute("loginUser") MemberDto loginUser
			){
		if(boardservice.detail(boardId) == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "BOARD_NOT_FOUND", "게시글을 찾을 수 없습니다.");
		}
		
		commentdto.setBoardId(boardId);
		commentdto.setUserId(loginUser.getUserId());
		
		commentservice.write(commentdto);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<Void> delete(
				@PathVariable int commentId,
				@RequestAttribute("loginUser") MemberDto loginUser
			){
		
		CommentDto comment = commentservice.detail(commentId);
		

		
		if(comment == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND", "댓글을 찾을 수 없습니다.");
		}
		
		if(comment.getUserId() != loginUser.getUserId()) {
			throw new ApiException(HttpStatus.FORBIDDEN, "FORBIDDEN", "댓글 삭제 권한이 없습니다.");
		}
		
		int result = commentservice.delete(commentId);
		log.info("댓글 삭제 결과 {}", result);
		
		return ResponseEntity.noContent().build();
	}
	
}
