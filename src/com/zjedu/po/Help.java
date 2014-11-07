package com.zjedu.po;

import java.io.Serializable;

public class Help implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String username = null;
	private String addr = null;
	private String content = null;
	private String time = null;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
