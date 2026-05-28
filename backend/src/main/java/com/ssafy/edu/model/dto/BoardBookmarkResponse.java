package com.ssafy.edu.model.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BoardBookmarkResponse {
	
	private boolean bookmarked;
	private int bookmarkCount;
	
}