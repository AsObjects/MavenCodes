package com.model;

public class MccUser {
	/*
	 *用户ID 
	 */
	private int id;
	/*
	 * 用户名
	 */
	private String userName;
	/*
	 * 密码
	 */
	private String passWord;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passWord="
				+ passWord + "]";
	}
}
