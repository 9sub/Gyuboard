package com.ssafy.edu.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageDto {
	
	private int page;
	private int size;
	private int totalCount;
	private int totalPage;
	private int offset; // db에서 얼마나 건너뛰고 가져올
	
	private int startPage;
	private int endPage;
	
	private boolean prev;
	private boolean next;
	
	public PageDto(int page, int size, int totalCount) {
		this.page = page;
		this.size =size;
		this.totalCount = totalCount;
		
		if(this.page<1) {
			this.page = 1;
		}
		
		if(this.size<1) {
			this.size = 10;
		}
		
		this.totalPage = (int) Math.ceil((double)totalCount / size);
		
		if(this.totalPage == 0) {
			this.totalPage = 1;
		}
		
		if(this.page >this.totalPage) {
			this.page = this.totalPage;
		}
		
		this.offset = (this.page-1) * this.size;
		
		int pageBlock = 5; // 한번에 페이지 블럭몇개?
		
		this.startPage = ((this.page - 1)/pageBlock) * pageBlock +1;
		this.endPage = this.startPage + pageBlock -1;
		
		if(this.endPage > this.totalPage){
			this.endPage = this.totalPage;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < this.totalPage;
		
	}
}
