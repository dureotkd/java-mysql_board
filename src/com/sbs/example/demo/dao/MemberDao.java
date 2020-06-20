package com.sbs.example.demo.dao;
import java.util.List;
import java.util.Map;

import com.sbs.example.demo.db.DBConnection;
import com.sbs.example.demo.dto.Member;
import com.sbs.example.demo.factory.Factory;

public class MemberDao {
	private DBConnection dbConnection;

	public MemberDao() {
		dbConnection = Factory.getDBConnection();
	}

	public Member getMemberByLoginIdAndLoginPw(String loginId, String loginPw) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `member` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND `loginId` = '%s' ", loginId));
		sb.append(String.format("AND `loginPw` = '%s' ", loginPw));

		Map<String, Object> row = dbConnection.selectRow(sb.toString());
		
		if ( row.isEmpty() ) {
			return null;
		}
		
		return new Member(row);
	}

	public Member getMemberByLoginId(String loginId) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `member` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND loginId = '%s' ", loginId));

		Map<String, Object> memberRow = dbConnection.selectRow(sb.toString());

		if (memberRow.isEmpty()) {
			return null;
		}

		return new Member(memberRow);
	}

	public Member getMember(int id) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `member` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND `id` = '%d' ", id));

		Map<String, Object> row = dbConnection.selectRow(sb.toString());
		
		if ( row.isEmpty() ) {
			return null;
		}
		
		return new Member(row);
	}

	public int save(Member member) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("INSERT INTO member "));
		sb.append(String.format("SET regDate = '%s' ", member.getRegDate()));
		sb.append(String.format(", loginId = '%s' ", member.getLoginId()));
		sb.append(String.format(", loginPw = '%s' ", member.getLoginPw()));
		sb.append(String.format(", `name` = '%s' ", member.getName()));
		sb.append(String.format(", phone = '%s' ", member.getPhone()));
		System.out.printf("[ %s ] 회원님 환영합니다 ^^\n",member.getName());
		return dbConnection.insert(sb.toString());
	}

	public int modify(String loginPw, String loginId) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("UPDATE "));
		sb.append(String.format("MEMBER "));
		sb.append(String.format("SET loginId = ' "+loginId+" ',"+" loginPw = ' "+loginPw+" '"));
		sb.append(String.format(" WHERE id = ' "+Factory.getSession().getLoginedMember().getId())+" '");
		
		System.out.println("회원정보가 변경되었습니다.");
		return dbConnection.update(sb.toString());
	}

	public int delete() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("DELETE "));
		sb.append(String.format("FROM MEMBER"));
		sb.append(String.format(" WHERE ID = '"+Factory.getSession().getLoginedMember().getId())+"'");
		
		System.out.println("회원정보가 삭제되었습니다.");
		return dbConnection.delete(sb.toString());
	}
	public int search(String phone) {
		List<Map<String, Object>> member  = getMemberByNameandPhone(phone);
		return 1;
	}

	public List<Map<String, Object>> getMemberByNameandPhone(String phone) {
		StringBuilder sb = new StringBuilder();
		
		String sql = "";
		sql += "SELECT * FROM member WHERE phone LIKE '%"+ phone + "%'";
		return Factory.getDBConnection().search(sql);
	}

	
}