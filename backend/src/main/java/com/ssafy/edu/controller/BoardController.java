package com.ssafy.edu.controller;


import java.io.IOException;
import java.util.List;
import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.nio.file.StandardCopyOption;

import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.edu.exception.ApiException;
import com.ssafy.edu.model.dto.BoardDetailResponse;
import com.ssafy.edu.model.dto.BoardDto;
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
public class BoardController {
	
	private final BoardService boardservice;
	private final CommentService commentservice;
	private final BoardLikeService boardlikeservice;
	private final BoardBookmarkService boardbookmarkservice;

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
				@RequestPart("board") BoardDto boarddto,
				@RequestPart(value = "image", required = false) MultipartFile image,
				@RequestAttribute("loginUser") MemberDto loginUser
			)throws IOException{
		boarddto.setUserId(loginUser.getUserId());
		
		if(image != null && !image.isEmpty()) {
			String contentType = image.getContentType();
			
			if(contentType == null || !contentType.startsWith("image/")) {
				throw new ApiException(HttpStatus.BAD_REQUEST, "INVALID_FILE_TYPE", "이미지 파일만 업로드할 수 있습니다.");
			}
			
			Path uploadPath = Paths.get("uploads","board")
					.toAbsolutePath()
					.normalize();
			
			Files.createDirectories(uploadPath);
			
			String originalFilename = image.getOriginalFilename();
			String extenstion = "";
			
			if(originalFilename != null && originalFilename.contains(".")) {
				extenstion = originalFilename.substring(originalFilename.lastIndexOf("."));
			}
			String savedFilename = UUID.randomUUID().toString() + extenstion;
			
			Path targetPath = uploadPath.resolve(savedFilename).normalize();
			
			Files.copy(
						image.getInputStream(),
						targetPath,
						StandardCopyOption.REPLACE_EXISTING
					);
			boarddto.setImageUrl("/uploads/board/"+savedFilename);
		}
		
		boardservice.write(boarddto);
		
		log.info("board dto = {}", boarddto);
		
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<BoardDetailResponse> detail(
				@PathVariable int id,
				@RequestAttribute("loginUser") MemberDto loginUser
			){
		 
		boardservice.updateViewCount(id);
		
		BoardDto boarddto = boardservice.detail(id);
		
		
		if(boarddto == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "BOARD_NOT_FOUND", "게시글을 찾을 수 없습니다.");
		}
		
		boarddto.setLikeCount(boardlikeservice.count(id));
		
		boarddto.setLiked(boardlikeservice.isLiked(id, loginUser.getUserId()));
		
		boarddto.setBookmarked(
				boardbookmarkservice.isBookmarked(id, loginUser.getUserId())
				);
		
		boarddto.setBookmarkCount(boardbookmarkservice.count(id));
		
		log.info("board {}",boarddto);
		
		List<CommentDto> comments = commentservice.list(id);
		
	
		
		BoardDetailResponse response = new BoardDetailResponse(boarddto, comments);
		
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(
				@PathVariable int id,
				@RequestBody BoardDto boarddto,
				@RequestAttribute("loginUser") MemberDto loginUser
			){
		
		BoardDto board = boardservice.detail(id);
		if(board == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "BOARD_NOT_FOUND", "게시글을 찾을 수 없습니다.");
		}
		if(loginUser.getUserId() != board.getUserId()) {
			throw new ApiException(HttpStatus.FORBIDDEN, "FORBIDDEN", "수정 권한이 없습니다.");
		}
		
		boarddto.setId(id);
		boarddto.setUserId(board.getUserId());

		
		boardservice.update(boarddto);
		
		
		return ResponseEntity.ok().build();
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(
				@PathVariable int id,
				@RequestAttribute("loginUser") MemberDto loginUser
			){
		
		BoardDto board = boardservice.detail(id);
		
		if(board == null) {
			throw new ApiException(HttpStatus.NOT_FOUND, "BOARD_NOT_FOUND", "게시글을 찾을 수 없습니다.");
		}
		if(loginUser.getUserId() != board.getUserId()) {
			throw new ApiException(HttpStatus.FORBIDDEN, "FORBIDDEN", "수정 권한이 없습니다.");
		}
		
		boardlikeservice.deleteByBoardId(id);
		commentservice.deleteByBoardId(id);
		boardservice.delete(id);
		
		return ResponseEntity.noContent().build();
		
	}
	
	
	
	
	
	
	
}
