package com.kh.board.model.service;

import static com.kh.common.JDBCTemplate.getConnection;
import static com.kh.common.JDBCTemplate.close;
import static com.kh.common.JDBCTemplate.commit;
import static com.kh.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.BoardComment;

public class BoardService {

	private BoardDao dao=new BoardDao();

	public List<Board> selectBoard(int cPage, int numPerPage) {

		Connection conn=getConnection();
		List<Board> list=dao.selectBoard(conn, cPage, numPerPage);
		close(conn);
		return list;
	}

	public int boardCount() {
		Connection conn=getConnection();
		int result=dao.boardCount(conn);
		close(conn);
		return result;
	}

	public Board selectBoard(int no, boolean hasRead) {
		Connection conn=getConnection();
		Board b=dao.selectBoard(conn, no);
		if(b!=null && !hasRead) { //쿠키에 읽은 상태라면 조회수 로직이 돌아가지않음
			int result=dao.updateReadCount(conn,no);
			if(result>0) {
				b.setBoardReadCount(dao.selectBoard(conn, no).getBoardReadCount());
				commit(conn);
			}
			else rollback(conn);
		}
		close(conn);
		return b;
	}

	public int insertBoard(Board b) {
		Connection conn=getConnection();
		int result=dao.insertBoard(conn, b);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public int insertComment(BoardComment bc) {
		Connection conn=getConnection();
		int result=dao.insertComment(conn, bc);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public List<BoardComment> selectBoardComment(int no) {
		Connection conn=getConnection();
		List<BoardComment> list=dao.selectBoardComment(conn,no);
		close(conn);
		return list;
	}
	
	
}
