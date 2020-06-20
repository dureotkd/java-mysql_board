package com.sbs.example.demo.service;

import java.util.List;

import com.sbs.example.demo.dto.Article;
import com.sbs.example.demo.dto.Board;
import com.sbs.example.demo.factory.Factory;
import com.sbs.example.demo.util.Util;

// Service
public class BuildService {
	private ArticleService articleService;
	private static boolean buildSite;
		static  {
			buildSite = false;
		}
	public BuildService() {
		articleService = Factory.getArticleService();
	}

	public void buildSite() {
		
		buildSite = true;
		
		new Thread(()->{
			while ( buildSite ) {
		Util.makeDir("site");
		Util.makeDir("site/article");
		
		String head = Util.getFileContents("site/article/head.html");
		String foot = Util.getFileContents("site/article/foot.html");

		// 각 게시판 별 게시물리스트 페이지 생성
		List<Board> boards = articleService.getBoards();

		for (Board board : boards) {
			String fileName = board.getCode() + "-list-1.html";

			String html = "";

			List<Article> articles = articleService.getArticlesByBoardCode(board.getCode());
			String template = Util.getFileContents("article/list.html");

			for (Article article : articles) {
				if ( board.getId() == article.getId()) {
					html += "<link rel=\"stylesheet\" href=\"Style.css\">";
					html += "<table border=2 height = 100px width = 500px >";
					html += "<tr>";
					html += "<td>" + article.getId() + "</td>";
					html += "<td>" + article.getRegDate() + "</td>";
					html += "<td><a href=\"" + article.getId() + ".html\">" + article.getTitle() + "</a></td>";
					html += "</tr>";
				}
			}
			
			html = head + html + foot;

			Util.writeFileContents("site/article/" + fileName, html);
		}
		// 게시물 별 파일 생성
		List<Article> articles = articleService.getArticles();

		for (Article article : articles) {
			String html = "";
			
			html += "<link rel=\"stylesheet\" href=\"Style.css\">";
			html += "<div>제목 : " + article.getTitle() + "</div>";
			html += "<div>내용 : " + article.getBody() + "</div>";
			html += "<div><a href=\"" + (article.getId() - 1) + ".html\">이전글</a></div>";
			html += "<div><a href=\"" + (article.getId() + 1) + ".html\">다음글</a></div>";
			
			html = head + html + foot;

			Util.writeFileContents("site/article/" + article.getId() + ".html", html);
				}
			}
		}).start();
		System.out.println("== 사이트 자동생성 중.. ==");
	}

	public void buildStop() {
		buildSite = false;
		System.out.println("== 사이트 자동생성 종료.. == ");
	}
}