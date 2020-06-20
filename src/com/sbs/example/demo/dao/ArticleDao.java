package com.sbs.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.demo.db.DBConnection;
import com.sbs.example.demo.dto.Article;
import com.sbs.example.demo.dto.ArticleReply;
import com.sbs.example.demo.dto.Board;
import com.sbs.example.demo.factory.Factory;

// Dao
public class ArticleDao {
	private DBConnection dbConnection;

	public ArticleDao() {
		dbConnection = Factory.getDBConnection();
	}

	public List<Article> getArticlesByBoardCode(String code) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT A.* "));
		sb.append(String.format("FROM `article` AS A "));
		sb.append(String.format("INNER JOIN `board` AS B "));
		sb.append(String.format("ON A.boardId = B.id "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND B.`code` = '%s' ", code));
		sb.append(String.format("ORDER BY A.id DESC "));

		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());
		
		for ( Map<String, Object> row : rows ) {
			articles.add(new Article(row));
		}
		
		return articles;
	}

	public List<Board> getBoards() {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `board` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("ORDER BY id DESC "));

		List<Board> boards = new ArrayList<>();
		List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());
		
		for ( Map<String, Object> row : rows ) {
			boards.add(new Board(row));
		}
		
		return boards;
	}

	public Board getBoardByCode(String code) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `board` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND `code` = '%s' ", code));

		Map<String, Object> row = dbConnection.selectRow(sb.toString());
		
		if ( row.isEmpty() ) {
			return null;
		}
		
		return new Board(row);
	}

	public int saveBoard(Board board) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("INSERT INTO board "));
		sb.append(String.format("SET regDate = '%s' ", board.getRegDate()));
		sb.append(String.format(", `code` = '%s' ", board.getCode()));
		sb.append(String.format(", `name` = '%s' ", board.getName()));

		return dbConnection.insert(sb.toString());
	}

	public int save(Article article) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("INSERT INTO article "));
		sb.append(String.format("SET regDate = '%s' ", article.getRegDate()));
		sb.append(String.format(", `title` = '%s' ", article.getTitle()));
		sb.append(String.format(", `body` = '%s' ", article.getBody()));
		sb.append(String.format(", `memberId` = '%d' ", article.getMemberId()));
		sb.append(String.format(", `boardId` = '%d' ", article.getBoardId()));

		return dbConnection.insert(sb.toString());
	}

	public Board getBoard(int id) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `board` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("AND `id` = '%d' ", id));

		Map<String, Object> row = dbConnection.selectRow(sb.toString());
		
		if ( row.isEmpty() ) {
			return null;
		}
		
		return new Board(row);
	}

	public List<Article> getArticles() {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `article` "));
		sb.append(String.format("WHERE 1 "));
		sb.append(String.format("ORDER BY id DESC "));

		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());
		
		for ( Map<String, Object> row : rows ) {
			articles.add(new Article(row));
		}
		
		return articles;
	}

	public int modify(int modifyId, String title, String body) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("UPDATE "));
		sb.append(String.format(" article "));
		sb.append(String.format("SET title ='" + title + "'," + "body='" + body + "' "));
		sb.append(String.format("WHERE id = '"+modifyId+"'"));
		System.out.printf("수정이 완료되었습니다.\n");
		return Factory.getDBConnection().update(sb.toString());
	}

	public int delete(int deleteId) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("DELETE "));
		sb.append(String.format("FROM article "));
		sb.append(String.format("WHERE ID = '%d' ",deleteId));
		System.out.printf("게시글 [%d] 이 삭제되었습니다.\n",deleteId);
		return Factory.getDBConnection().delete(sb.toString());
	}

	public List<Map<String, Object>> search(String search) {
		StringBuilder sb = new StringBuilder();
		
		String sql = "";
		sql += "SELECT * FROM article WHERE title  LIKE '%"+ search + "%'";
		return Factory.getDBConnection().searchA(sql);
	}
	

	public int reply(ArticleReply articleReply) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("INSERT INTO articleReply "));
		sb.append(String.format("SET regDate = '%s' ",articleReply.getRegDate()));
		sb.append(String.format(", `body` = '%s' ",articleReply.getBody()));
		sb.append(String.format(", `articleId` = '%d' ",articleReply.getArticleId()));
		sb.append(String.format(", `memberId` = '%d'", articleReply.getMemberId()));
		System.out.println("댓글 작성이 완료되었습니다.");
		return Factory.getDBConnection().insert(sb.toString());
	}

	public int modifyReply(String body, int replyId) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("UPDATE "));
		sb.append(String.format("articleReply "));
		sb.append(String.format("SET `body` = '" + body + "'" ));
		sb.append(String.format("WHERE id = '" + replyId + "'"));
		System.out.printf("댓글 수정이 완료되었습니다.\n");
		return Factory.getDBConnection().update(sb.toString());
	}

	public List<Map<String, Object>> getArticleReplyById(int replyId) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT * "));
		sb.append(String.format("FROM `articleReply` "));
		sb.append(String.format("WHERE `id` = '%d' ", replyId));
		return Factory.getDBConnection().selectRows(sb.toString());
		
		
	}

	public Article getForPrintArticle(int id) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT A.*, M.name AS extra__writerName "));
		sb.append(String.format("FROM `article` AS A "));
		sb.append(String.format("INNER JOIN `member` AS M "));
		sb.append(String.format("ON A.memberId = M.id "));
		sb.append(String.format("WHERE A.id = '%d' ", id));

		String sql = sb.toString();
		Map<String, Object> row = dbConnection.selectRow(sql);

		if (row.isEmpty()) {
			return null;
		}

		return new Article(row);
	}

	public List<ArticleReply> getForPrintArticleRepliesByArticleId(int articleId) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("SELECT AR.*, M.name AS extra__writerName "));
		sb.append(String.format("FROM `articleReply` AS AR "));
		sb.append(String.format("INNER JOIN `member` AS M "));
		sb.append(String.format("ON AR.memberId = M.id "));
		sb.append(String.format("WHERE AR.articleId = '%d' ", articleId));
		sb.append(String.format("ORDER BY AR.id DESC "));

		List<ArticleReply> articleReplies = new ArrayList<>();
		List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());

		for (Map<String, Object> row : rows) {
			articleReplies.add(new ArticleReply(row));
		}

		return articleReplies;
	}

	public List<ArticleReply> getArticleRepliesId() {
			StringBuilder sb = new StringBuilder();

			sb.append(String.format("SELECT * "));
			sb.append(String.format("FROM `articleReply` "));
			sb.append(String.format("WHERE 1 "));
			sb.append(String.format("ORDER BY id ASC "));

			List<ArticleReply> articleReplies = new ArrayList<>();
			List<Map<String, Object>> rows = dbConnection.selectRows(sb.toString());
			
			for ( Map<String, Object> row : rows ) {
				articleReplies.add(new ArticleReply(row));
			}
			return articleReplies;
		}

	public int getReplies(int deleteId) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("DELETE "));
		sb.append(String.format("FROM articleReply "));
		sb.append(String.format("WHERE ID = ' %d ' ",deleteId));
		System.out.printf("댓글 [%d] 이 삭제되었습니다.\n",deleteId);
		return Factory.getDBConnection().delete(sb.toString());
	}
	

	
}