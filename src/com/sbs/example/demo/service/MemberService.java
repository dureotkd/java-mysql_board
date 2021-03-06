package com.sbs.example.demo.service;

import com.sbs.example.demo.dao.MemberDao;
import com.sbs.example.demo.dto.Member;
import com.sbs.example.demo.factory.Factory;

public class MemberService {
	private MemberDao memberDao;

	public MemberService() {
		memberDao = Factory.getMemberDao();
	}

	public Member getMemberByLoginIdAndLoginPw(String loginId, String loginPw) {
		return memberDao.getMemberByLoginIdAndLoginPw(loginId, loginPw);
	}

	public int join(String loginId, String loginPw, String name , String phone ) {
		Member oldMember = memberDao.getMemberByLoginId(loginId);

		if (oldMember != null) {
			return -1;
		}

		Member member = new Member(loginId, loginPw, name , phone );
		return memberDao.save(member);
	}

	public Member getMember(int id) {
		return memberDao.getMember(id);
	}

	public void makeAdminUserIfNotExists() {
		Member member = memberDao.getMemberByLoginId("admin");
		
		if (member == null) {
			join("admin", "admin", "관리자","1");
		}
	}

	public boolean isUsedLoginId(String loginId) {
		Member member = memberDao.getMemberByLoginId(loginId);
		
		if ( member == null ) {
			return false;
		}
		return true;
	}

	public int modify(String loginPw, String loginId) {
		return memberDao.modify(loginPw,loginId);
	}

	public int delete() {
		return memberDao.delete();
	}

	public int search( String phone) {
		return memberDao.search(phone);
	}
}