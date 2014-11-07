package com.zjedu.po;

import java.io.Serializable;

public class HelpReceive implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int id = 0;
	private int helpid = 0;
	private String username = null;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getHelpid() {
		return helpid;
	}
	public void setHelpid(int helpid) {
		this.helpid = helpid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	

}
