package com.kh.board.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.BoardComment;

public class BoardDao {
	
	private Properties prop=new Properties();

	public BoardDao() {
		try {
			String path=BoardDao.class.getResource("/sql/board/board-query.properties").getPath();
			prop.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public List<Board> selectBoard(Connection conn, int cPage, int numPerPage) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Board> list=new ArrayList<Board>();
		Board b=null;
		String sql=prop.getProperty("searchBoard");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, (cPage-1)*numPerPage+1);
			pstmt.setInt(2, cPage*numPerPage);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				b=new Board();
				b.setBoardNo(rs.getInt("BOARD_NO"));
				b.setBoardTitle(rs.getString("BOARD_TITLE"));
				b.setBoardWriter(rs.getString("BOARD_WRITER"));
				b.setBoardContent(rs.getString("BOARD_CONTENT"));
				b.setBoardOriginalFileName(rs.getString("BOARD_ORIGINAL_FILENAME"));
				b.setBoardRenamedFileName(rs.getString("BOARD_RENAMED_FILENAME"));
				b.setBoardDate(rs.getDate("BOARD_DATE"));
				b.setBoardReadCount(rs.getInt("BOARD_READCOUNT"));
				list.add(b);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return list;
	}
	public int boardCount(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=prop.getProperty("boardCount");
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}
	public Board selectBoard(Connection conn, int no) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Board b=null;
		String sql=prop.getProperty("selectBoardView");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				b=new Board();
				b.setBoardNo(rs.getInt("BOARD_NO"));
				b.setBoardTitle(rs.getString("BOARD_TITLE"));
				b.setBoardWriter(rs.getString("BOARD_WRITER"));
				b.setBoardContent(rs.getString("BOARD_CONTENT"));
				b.setBoardOriginalFileName(rs.getString("BOARD_ORIGINAL_FILENAME"));
				b.setBoardRenamedFileName(rs.getString("BOARD_RENAMED_FILENAME"));
				b.setBoardDate(rs.getDate("BOARD_DATE"));
				b.setBoardReadCount(rs.getInt("BOARD_READCOUNT"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return b;
	}
	public int updateReadCount(Connection conn, int no) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("updateReadCount");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	public int insertNotice(Connection conn, Board b) {
		// TODO Auto-generated method stub
		return 0;
	}
	public int insertComment(Connection conn, BoardComment bc) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("insertComment");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bc.getBoardCommentLevel());
			pstmt.setString(2, bc.getBoardCommentWriter());
			pstmt.setString(3, bc.getBoardCommentContent());
			pstmt.setInt(4, bc.getBoardRef());
			pstmt.setString(5, bc.getBoardCommentRef()==0?null:String.valueOf(bc.getBoardCommentRef()));
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	public List<BoardComment> selectBoardComment(Connection conn, int no) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=prop.getProperty("selectBoardComment");
		List<BoardComment> list=new ArrayList<BoardComment>();
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				BoardComment bc=new BoardComment();
//				 "BOARD_COMMENT_NO" NUMBER,
//				    "BOARD_COMMENT_LEVEL" NUMBER DEFAULT 1,
//				    "BOARD_COMMENT_WRITER" VARCHAR2(15), 
//				    "BOARD_COMMENT_CONTENT" VARCHAR2(2000), 
//				    "BOARD_REF" NUMBER, 
//				    "BOARD_COMMENT_REF" NUMBER, 
//				    "BOARD_COMMENT_DATE" DATE DEFAULT SYSDATE, 
				bc.setBoardCommentNo(rs.getInt("BOARD_COMMENT_NO"));
				bc.setBoardCommentLevel(rs.getInt("BOARD_COMMENT_LEVEL"));
				bc.setBoardCommentWriter(rs.getString("BOARD_COMMENT_WRITER"));
				bc.setBoardCommentContent(rs.getString("BOARD_COMMENT_CONTENT"));
				bc.setBoardRef(rs.getInt("BOARD_REF"));
				bc.setBoardCommentRef(rs.getInt("BOARD_COMMENT_REF"));
				bc.setBoardCommentDate(rs.getDate("BOARD_COMMENT_DATE"));
				list.add(bc);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return list;
	}
	public int insertBoard(Connection conn, Board b) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("insertBoard");
		try {
			pstmt=conn.prepareStatement(sql);
//		    "BOARD_NO" NUMBER, 
//		    "BOARD_TITLE" VARCHAR2(50), 
//		    "BOARD_WRITER" VARCHAR2(15), 
//		    "BOARD_CONTENT" VARCHAR2(2000), 
//		    "BOARD_ORIGINAL_FILENAME" VARCHAR2(100), 
//		    "BOARD_RENAMED_FILENAME" VARCHAR2(100), 
//		    "BOARD_DATE" DATE DEFAULT SYSDATE, 
//		    "BOARD_READCOUNT" NUMBER DEFAULT 0, 
			pstmt.setString(1,b.getBoardTitle());
			pstmt.setString(2,b.getBoardWriter());
			pstmt.setString(3,b.getBoardContent());
			pstmt.setString(4,b.getBoardOriginalFileName());
			pstmt.setString(5,b.getBoardRenamedFileName());
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

}
