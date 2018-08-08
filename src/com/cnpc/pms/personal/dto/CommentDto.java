package com.cnpc.pms.personal.dto;


import java.util.Date;

public class CommentDto {

	private Long time;
	private String message;
	
	
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
}
