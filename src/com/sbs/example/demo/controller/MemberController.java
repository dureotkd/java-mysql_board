package com.sbs.example.demo.controller;

import com.sbs.example.demo.dto.Member;
import com.sbs.example.demo.factory.Factory;
import com.sbs.example.demo.service.MemberService;

public class MemberController extends Controller {
	private MemberService memberService;

	public MemberController() {
		memberService = Factory.getMemberService();
	}

	public void doAction(Request reqeust) {
		if (reqeust.getActionName().equals("logout")) {
			actionLogout(reqeust);
		} else if (reqeust.getActionName().equals("login")) {
			actionLogin(reqeust);
		} else if (reqeust.getActionName().equals("whoami")) {
			actionWhoami(reqeust);
		} else if (reqeust.getActionName().equals("join")) {
			actionJoin(reqeust);
		} else if (reqeust.getActionName().equals("modify")) {
			actionModify(reqeust);
		} else if (reqeust.getActionName().equals("delete")) {
			actionDelete(reqeust);
		} else if (reqeust.getActionName().equals("search")) {
			actionIdsearch(reqeust);
		} 
	}


	private void actionIdsearch(Request reqeust) {
		Member loginedMember = Factory.getSession().getLoginedMember();
		if ( loginedMember != null ) {
			System.out.println("로그아웃 후 이용해주세요.");
			return;
		}
		
		System.out.println("== 회원정보 찾기 ==");

		System.out.print("핸드폰 번호를 입력해주세요 : ");
		String phone = Factory.getScanner().next().trim();
		
		int re = Factory.getMemberService().search(phone);
	}

	private void actionDelete(Request reqeust) {
		Member loginedMember = Factory.getSession().getLoginedMember();
		String loginId;
		String loginPw;
		if ( loginedMember == null ) {
			System.out.println("로그인이 필요한 기능 입니다.");
		}
		System.out.println("== 회원정보 삭제 == ");
		
		while ( true ) {
			System.out.println("ID : ");
			loginId = Factory.getScanner().next().trim();
			
			if ( loginId.length() == 0 ) {
				System.out.println("아이디를 입력해주세요.");
				continue;
			}
			
			if ( loginId.equals(Factory.getSession().getLoginedMember().getLoginId())==false) {
				System.out.println("아이디가 일치하지 않습니다.");
				continue;
			}
			break;
		}
		
		while ( true ) {
			System.out.println("PW : ");
			loginPw = Factory.getScanner().next().trim();
			
			if ( loginPw.length() == 0 ) {
				System.out.println("비밀번호를 입력해주세요.");
				continue;
			}
			
			if ( loginPw.equals(Factory.getSession().getLoginedMember().getLoginPw())==false) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				continue;
			}
			break;
		}
		
		int rd = Factory.getMemberService().delete();
		
	}

	private void actionModify(Request reqeust) {
		Member loginedMember = Factory.getSession().getLoginedMember();
		String loginPw;
		String loginId;
		String loginPwConfirm;
		String name;
		if ( loginedMember == null ) {
			System.out.println("로그인이 필요한 기능입니다.");
			return;
		}
		System.out.println("== 회원정보 수정 ==");
		while(true) {
			System.out.print("수정 ID : ");
		    loginId = Factory.getScanner().next().trim();
			if ( loginId.length() == 0 ) {
				System.out.println("수정 아이디를 입력해주세요.");
				continue;
			}
			
			if ( loginId.length() < 2 ) {
				System.out.println("수정 아이디를 2글자 이상 입력해주세요.");
				continue;
			}
			
			if ( Factory.getMemberService().isUsedLoginId(loginId)) {
				System.out.println("이미 사용중인 아이디입니다.");
				continue;
			}
			
			break;
		}
	while ( true ) {
		boolean loginPwVaild = true;
		while(true) {
			System.out.print("수정 PW : ");
			loginPw = Factory.getScanner().next().trim();
			if ( loginPw.length() == 0 ) {
				System.out.println("수정 비밀번호를 입력해주세요.");
				continue;
			}
			
			if ( loginPw.length() < 2 ) {
				System.out.println("수정 비밀번호를 2글자 이상 입력해주세요.");
				continue;
			}
			
			break;
		}
		
		while(true) {
			System.out.print("수정 PW 확인 : ");
			loginPwConfirm = Factory.getScanner().next().trim();
			if ( loginPwConfirm.length() == 0 ) {
				System.out.println("수정 비밀번호를 입력해주세요.");
				continue;
			}
			if (loginPw.equals(loginPwConfirm) == false ) {
				System.out.println("비밀번호와 확인이 일치하지 않습니다.");
				continue;
			}
			
			break;
		}
			if ( loginPwVaild == true ) {
				break;
			}
		}	
		int rm = Factory.getMemberService().modify(loginPw,loginId);
			
		
	}

	private void actionJoin(Request reqeust) {
		Member loginedMember = Factory.getSession().getLoginedMember();
		String name;
		String loginPw;
		String loginPwConfirm;
		String loginId;
		String phone;
		if ( loginedMember != null ) {
			System.out.println("로그아웃 먼저 해주세요.");
		}
		
		if ( loginedMember == null ) {
			System.out.println("== 회원가입 시작 ==");
		while ( true ) {
				System.out.print("name : ");
			    name = Factory.getScanner().next().trim();
				
				if ( name.length() == 0 ) {
					System.out.println("이름을 입력해주세요.");
					continue;
				}
				
				if ( name.length() < 2 ) {
					System.out.println("이름을 2글자 이상 입력해주세요.");
					continue;
				}
				break;
			}	
			
		while ( true ) {
			System.out.print("ID : ");
		    loginId = Factory.getScanner().next().trim();
			
			if ( loginId.length() == 0 ) {
				System.out.println("아이디를 입력해주세요.");
				continue;
			}
			
			if ( loginId.length() < 2 ) {
				System.out.println("아이디를 2글자 이상 입력해주세요.");
				continue;
			}
			
			if ( Factory.getMemberService().isUsedLoginId(loginId)){
				System.out.println("이미 사용중인 아이디입니다.");
				continue;
			}
			
			break;
		}
	while ( true ) {
			boolean loginPwVaild = true;
		while ( true ) {
			
			System.out.print("PW : ");
			loginPw = Factory.getScanner().next().trim();
			
			if ( loginPw.length() == 0 ) {
				System.out.println("비밀번호를 입력해주세요.");
				continue;
			}
			
			if ( loginPw.length() < 2 ) {
				System.out.println("비밀번호를 2글자 이상 입력해주세요.");
				continue;
			}
			break;
			}
		
		while ( true ) {
			System.out.print("PW 확인 : ");
			loginPwConfirm = Factory.getScanner().next().trim();
			
			if (loginPw.equals(loginPwConfirm) == false ) {
				System.out.println("비밀번호와 확인이 일치하지 않습니다.");
				continue;
			}
			break;
			}
		
		while ( true ) {
			System.out.print("핸드폰 번호  : ");
			phone = Factory.getScanner().next().trim();
			break;
			}
		
		
		
		
				if ( loginPwVaild == true ) {
					break;
				}
			}
			int rs = Factory.getMemberService().join(loginId, loginPw, name , phone );
		} 
	}

	private void actionWhoami(Request reqeust) {
		Member loginedMember = Factory.getSession().getLoginedMember();

		if (loginedMember == null) {
			System.out.println("==NULL==");
		} else {
			System.out.println(loginedMember.getName());
		}

	}

	private void actionLogin(Request reqeust) {
		System.out.printf("로그인 아이디 : ");
		String loginId = Factory.getScanner().nextLine().trim();

		System.out.printf("로그인 비번 : ");
		String loginPw = Factory.getScanner().nextLine().trim();

		Member member = memberService.getMemberByLoginIdAndLoginPw(loginId, loginPw);

		if (member == null) {
			System.out.println("일치하는 회원이 없습니다.");
		} else {
			System.out.println(member.getName() + "님 환영합니다.");
			Factory.getSession().setLoginedMember(member);
		}
	}

	private void actionLogout(Request reqeust) {
		Member loginedMember = Factory.getSession().getLoginedMember();

		if (loginedMember != null) {
			Session session = Factory.getSession();
			System.out.println("로그아웃 되었습니다.");
			session.setLoginedMember(null);
		}

	}
}