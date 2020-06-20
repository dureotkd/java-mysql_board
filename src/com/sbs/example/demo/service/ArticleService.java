package com.sbs.example.demo.service;

import java.util.List;
import java.util.Map;

import com.sbs.example.demo.dao.ArticleDao;
import com.sbs.example.demo.dto.Article;
import com.sbs.example.demo.dto.ArticleReply;
import com.sbs.example.demo.dto.Board;
import com.sbs.example.demo.factory.Factory;

public class ArticleService {
	private ArticleDao articleDao;

	
	public ArticleService() {
		articleDao = Factory.getArticleDao();
	}

	public List<Article> getArticlesByBoardCode(String code) {
		return articleDao.getArticlesByBoardCode(code);
	}

	public List<Board> getBoards() {
		return articleDao.getBoards();
	}

	public int makeBoard(String name, String code) {
		Board oldBoard = articleDao.getBoardByCode(code);

		if (oldBoard != null) {
			return -1;
		}

		Board board = new Board(name, code);
		return articleDao.saveBoard(board);
	}

	public Board getBoard(int id) {
		return articleDao.getBoard(id);
	}

	public int write(int boardId, int memberId, String title, String body) {
		Article article = new Article(boardId, memberId, title, body);
		return articleDao.save(article);
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public void makeBoardIfNotExists(String name, String code) {
		Board board = articleDao.getBoardByCode(code);
		
		if ( board == null ) {
			makeBoard(name, code);
		}
	}

	public Board getBoardByCode(String boardCode) {
		return articleDao.getBoardByCode(boardCode);
	}

	public int modify(int modifyId, String title, String body) {
		return articleDao.modify(modifyId,title,body);
	}

	public int delete(int deleteId) {
		return articleDao.delete(deleteId);
	}

	public List<Map<String, Object>> search(String search) {
		return articleDao.search(search);
	}

	public int reply(int articleId, String body , int memberId) {
		ArticleReply articleReply = new ArticleReply(body, articleId,memberId);
		return articleDao.reply(articleReply);
	}

	public int modifyReply(String body,int replyId) {
		return articleDao.modifyReply(body,replyId);
	}

	public Article getForPrintArticle(int id) {
		return articleDao.getForPrintArticle(id);
	}

	public List<ArticleReply> getForPrintArticleRepliesByArticleId(int id) {
		return articleDao.getForPrintArticleRepliesByArticleId(id);
	}

	

	public List<Map<String, Object>> getArticleReplyById(int replyId) {
		return articleDao.getArticleReplyById(replyId);
	}

	public List<ArticleReply> getArticleRepliesId() {
		return articleDao.getArticleRepliesId();
	}

	public int deleteReply(int deleteId) {
		return articleDao.getReplies(deleteId);
	}

}