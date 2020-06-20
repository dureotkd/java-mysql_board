package com.sbs.example.demo.controller;

import java.util.List;
import java.util.Map;

import com.sbs.example.demo.dto.Article;
import com.sbs.example.demo.dto.ArticleReply;
import com.sbs.example.demo.dto.Board;
import com.sbs.example.demo.dto.Member;
import com.sbs.example.demo.factory.Factory;
import com.sbs.example.demo.service.ArticleService;

public class ArticleController extends Controller {
	private ArticleService articleService;

	public ArticleController() {
		articleService = Factory.getArticleService();
	}

	/* 추가 , 수정 , 삭제 , 게시판 바꾸기 , 최신 게시판 , 리스트 완료 */
	public void doAction(Request reqeust) {
		if (reqeust.getActionName().equals("list")) {
			actionList(reqeust);
		} else if (reqeust.getActionName().equals("write")) {
			actionWrite(reqeust);
		} else if (reqeust.getActionName().equals("changeBoard")) {
			actionChangeBoard(reqeust);
		} else if (reqeust.getActionName().equals("currentBoard")) {
			actionCurrentBoard(reqeust);
		} else if (reqeust.getActionName().equals("modify")) {
			actionModify(reqeust);
		} else if (reqeust.getActionName().equals("delete")) {
			actionDelete(reqeust);
		} else if (reqeust.getActionName().equals("search")) {
			actionSearch(reqeust);
		} else if (reqeust.getActionName().equals("reply")) {
			actionReply(reqeust);
		} else if ( reqeust.getActionName().equals("modifyReply")) {
			actionReplyModify(reqeust);
		} else if ( reqeust.getActionName().equals("detail")) {
			actionDetail(reqeust);
		} else if (reqeust.getActionName().equals("deleteReply")) {
			actionReplyDelete(reqeust);
		}
	}
	
	private void actionReplyDelete(Request reqeust) {
		int deleteId;
		List<ArticleReply> articleReplies = Factory.getArticleService().getArticleRepliesId();
		
		try {
			deleteId = Integer.parseInt(reqeust.getArg1());
		} catch (NumberFormatException e ) {
			System.out.println("댓글 번호를 숫자로 입력해주세요.");
			return;
		}
		
		for ( ArticleReply articleReply : articleReplies ) {
			int deleteReply = articleService.deleteReply(deleteId);
	
		}
		System.out.println("== 댓글 삭제 시작 == ");
		
		
		
		
	}

	private void actionDetail(Request reqeust) {
		int id;
		try {
			id = Integer.parseInt(reqeust.getArg1());
		} catch (NumberFormatException e) {
			System.out.println("게시물 번호를 숫자로 입력해주세요.");
			return;
		}

		Article article = articleService.getForPrintArticle(id);

		if (article == null) {
			System.out.println("해당 게시물은 존재하지 않습니다.");
			return;
		}
		
		String writerName = (String)article.getExtra().get("writerName");
		
		List<ArticleReply> articleReplies = articleService.getForPrintArticleRepliesByArticleId(article.getId());
		int repliesCount = articleReplies.size();
		
		System.out.printf("== %d번 게시물 상세 시작 ==\n", article.getId());
		System.out.printf("번호 : %d\n", article.getId());
		System.out.printf("작성날짜 : %s\n", article.getRegDate());
		System.out.printf("제목 : %s\n", article.getTitle());
		System.out.printf("내용 : %s\n", article.getBody());
		System.out.printf("작성자번호 : %s\n\n", writerName);
		System.out.printf("댓글개수 : %d\n", repliesCount);
		
		for ( ArticleReply articleReply : articleReplies ) {
			String replyWriterName = (String)articleReply.getExtra().get("writerName");
			System.out.println();
			System.out.printf("%d번 댓글 : %s by %s\n", articleReply.getId(), articleReply.getBody(), replyWriterName);
		}
		
		System.out.printf("== %d번 게시물 상세 끝 ==\n", article.getId());
		System.out.println();
	}
	// article modifyReply 23

	private void actionReplyModify(Request reqeust) {
		
		List<ArticleReply> articleReply = articleService.getArticleRepliesId();
		Member member = Factory.getSession().getLoginedMember();
		int replyId = Integer.parseInt(reqeust.getArg1());
		if ( member == null ) {
			System.out.println("== 로그인이 필요한 기능입니다. ==");
			return;
		}
		System.out.println("== 댓글 수정 시작 == ");
		for ( ArticleReply articleReplies : articleReply ) {
			System.out.println(articleReplies.getBody());
			if ( replyId == articleReplies.getId() ) {
				
				System.out.println(" 댓글 수정 : ");
				String body = Factory.getScanner().next();
				
				if ( body.length() == 0 ) {
					System.out.println(" 댓글을 입력해주세요 . ");
					continue;
				}
				int modifyReply = Factory.getArticleService().modifyReply(body,replyId);
			} else {
				System.out.println("존재하지 않는 댓글 번호 입니다.");
			}
			break;
		}
		
		// 문자열을 숫자로 변환해줌 Integer.parseInt(reqeust.getArg());
		
		
	}

	private void actionReply(Request reqeust) {
		Member member = Factory.getSession().getLoginedMember();
		List<Article> article = Factory.getArticleService().getArticles();
		if ( member == null ) {
			System.out.println("로그인이 필요한 기능입니다.");
			return;
		}
		
		System.out.println("== 게시글 댓글 시작 ==");
		
		System.out.println("댓글달 게시글 번호 : ");
		int articleId = Factory.getScanner().nextInt();
		
		for ( Article articles : article ) {
			if ( articles.getId() == articleId ) {
				System.out.printf("번호 [%d]\n",articles.getId());
				System.out.printf("제목 %s\n",articles.getTitle());
				System.out.printf("내용 %s\n",articles.getTitle());
				System.out.println("");
			}
		}
		String body;
		System.out.println("");
		
		while ( true ) {
			System.out.println(" 댓글 : ");
		    body = Factory.getScanner().next().trim();
		    
		    if ( body.length() == 0 ) {
		    	System.out.println("댓글을 입력해주세요.");
		    	continue;
		    }
		    break;
		}
		int memberId = Factory.getSession().getLoginedMember().getId();
		
		int articleReplies = Factory.getArticleService().reply(articleId,body,memberId);
		
		
		
	}

	private void actionSearch(Request reqeust) {
		System.out.println("== 게시글 검색 시작 ==");
		
		
		System.out.println("관련 검색어 : ");
		String search = Factory.getScanner().next().trim();
		
		List<Map<String, Object>> ss = Factory.getArticleService().search(search);
	}

	private void actionDelete(Request reqeust) {
		Member member = Factory.getSession().getLoginedMember();
		List<Article> article = Factory.getArticleService().getArticles();
		
		if ( member == null ) {
			System.out.println("로그인이 필요한 기능입니다.");
			return;
		}
		System.out.println(article+"\n");
		for ( Article articles : article ) {
			if ( articles.getMemberId() == member.getId() ) {
				System.out.println("== 게시글 삭제 시작 ==");
				
				System.out.println("삭제 하실 게시글 번호를 입력해주세요 : ");
				int deleteId = Factory.getScanner().nextInt();
				
				int id = Factory.getArticleService().delete(deleteId);
				return;
			}
			
		}
		
	}

	private void actionModify(Request reqeust) {
		Member member = Factory.getSession().getLoginedMember();
		List<Article> article = Factory.getArticleService().getArticles();
		
		if ( member == null ) {
			System.out.println("로그인이 필요한 기능입니다.");
			return;
		}
	
		System.out.println(article+"\n");
		
		for ( Article articles : article ) {
			if ( articles.getMemberId() == member.getId() ) {
		System.out.println("== 게시글 수정 시작 == ");
		
		System.out.println("수정 하실 게시글 번호를 입력해주세요 : ");
		int modifyId = Factory.getScanner().nextInt();
		
		System.err.println(" 수정 제목 : ");
		String title = Factory.getScanner().next().trim();
		System.err.println(" 수정 내용 : ");
		String body = Factory.getScanner().next().trim();
		
		int id = Factory.getArticleService().modify(modifyId,title,body);
			}
		}
	}

	private void actionCurrentBoard(Request reqeust) {
		Board board = Factory.getSession().getCurrentBoard();
		System.out.printf("현재 게시판 : %s\n", board.getName());
	}

	private void actionChangeBoard(Request reqeust) {
		String boardCode = reqeust.getArg1();

		Board board = articleService.getBoardByCode(boardCode);
		
		
		
		if (board == null) {
			System.out.println("해당 게시판이 존재하지 않습니다.");
		} else {
			System.out.printf("%s 게시판으로 변경되었습니다.\n", board.getName());
			Factory.getSession().setCurrentBoard(board);
		}
	}

	private void actionList(Request reqeust) {
		Board currentBoard = Factory.getSession().getCurrentBoard();
		List<Article> articles = articleService.getArticlesByBoardCode(currentBoard.getCode());

		System.out.printf("== %s 게시물 리스트 시작 ==\n", currentBoard.getName());
		for (Article article : articles) {
			System.out.printf("%d, %s, %s\n", article.getId(), article.getRegDate(), article.getTitle());
		}
		System.out.printf("== %s 게시물 리스트 끝 ==\n", currentBoard.getName());
	}

	private void actionWrite(Request reqeust) {
		Member member = Factory.getSession().getLoginedMember();
		if ( member == null ) {
			System.out.println("로그인이 필요한 기능입니다.");
			return;
		}
		System.out.printf("제목 : ");
		String title = Factory.getScanner().nextLine();
		System.out.printf("내용 : ");
		String body = Factory.getScanner().nextLine();

		// 현재 게시판 id 가져오기
		int boardId = Factory.getSession().getCurrentBoard().getId();

		// 현재 로그인한 회원의 id 가져오기
		int memberId = Factory.getSession().getLoginedMember().getId();
		int newId = articleService.write(boardId, memberId, title, body);

		System.out.printf("%d번 글이 생성되었습니다.\n", newId);
	}
}