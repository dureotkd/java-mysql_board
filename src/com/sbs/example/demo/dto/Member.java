package com.sbs.example.demo.dto;

import java.util.Map;

public class Member extends Dto {
	private String loginId;
	private String loginPw;
	private String name;
	private String phone;
	
	
	public Member() {

	}

	public Member(String loginId, String loginPw, String name, String phone) {
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.name = name;
		this.phone = phone;
	}

	public Member(Map<String, Object> row) {
		super((int) row.get("id"), (String) row.get("regDate"));
		this.loginId = (String) row.get("loginId");
		this.loginPw = (String) row.get("loginPw");
		this.name = (String) row.get("name");
		this.phone = (String) row.get("phone");
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginPw() {
		return loginPw;
	}

	public void setLoginPw(String loginPw) {
		this.loginPw = loginPw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}