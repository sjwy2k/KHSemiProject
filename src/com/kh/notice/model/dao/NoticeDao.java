package com.kh.notice.model.dao;

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

import com.kh.notice.model.vo.Notice;

public class NoticeDao {
	
	private Properties prop=new Properties();
	
	public NoticeDao() {
		try {
			String path=NoticeDao.class.getResource("/sql/notice/notice-query.properties").getPath();
			prop.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	public List<Notice> searchNotice(Connection conn, int cPage, int numPerPage) {

		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Notice> list=new ArrayList<Notice>();
		Notice n=null;
		String sql=prop.getProperty("searchNotice");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, (cPage-1)*numPerPage+1);
			pstmt.setInt(2, cPage*numPerPage);
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				n=new Notice();
				n.setNoticeNo(rs.getInt("NOTICE_NO"));
				n.setNoticeTitle(rs.getString("NOTICE_TITLE"));
				n.setNoticeWriter(rs.getString("NOTICE_WRITER"));
				n.setNoticeContent(rs.getString("NOTICE_CONTENT"));
				n.setNoticeDate(rs.getDate("NOTICE_DATE"));
				n.setFilePath(rs.getString("FILEPATH"));
				n.setStatus(rs.getString("STATUS"));
				list.add(n);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return list;
	}

	public int noticeCount(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=prop.getProperty("noticeCount");
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

//	public Notice searchNoticeForNo(Connection conn, int no) {
//		PreparedStatement pstmt=null;
//		ResultSet rs=null;
//		Notice n=null;
//		String sql=prop.getProperty("searchNoticeForNo");
//		try {
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setInt(1, no);
//			rs=pstmt.executeQuery();
//			
//			while(rs.next()) {
//				n=new Notice();
//				n.setNoticeNo(rs.getInt("NOTICE_NO"));
//				n.setNoticeTitle(rs.getString("NOTICE_TITLE"));
//				n.setNoticeWriter(rs.getString("NOTICE_WRITER"));
//				n.setNoticeContent(rs.getString("NOTICE_CONTENT"));
//				n.setNoticeDate(rs.getDate("NOTICE_DATE"));
//				n.setFilePath(rs.getString("FILEPATH"));
//				n.setStatus(rs.getString("STATUS"));
//			}
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}finally {
//			close(rs);
//			close(pstmt);
//		}
//		return n;
//	}

	public Notice selectNotice(Connection conn, int no) {

		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=prop.getProperty("selectNotice");
		Notice n=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				n=new Notice();
				n.setNoticeNo(rs.getInt("NOTICE_NO"));
				n.setNoticeTitle(rs.getString("NOTICE_TITLE"));
				n.setNoticeWriter(rs.getString("NOTICE_WRITER"));
				n.setNoticeContent(rs.getString("NOTICE_CONTENT"));
				n.setNoticeDate(rs.getDate("NOTICE_DATE"));
				n.setFilePath(rs.getString("FILEPATH"));
//				n.setStatus(rs.getString("STATUS"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return n;//null이거나 값이 있거나
	}

	public int insertNotice(Connection conn, Notice n) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("insertNotice");
		try {
			pstmt=conn.prepareStatement(sql);
			//title, writer, content, filepath
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeWriter());
			pstmt.setString(3, n.getNoticeContent());
			pstmt.setString(4, n.getFilePath());
			result=pstmt.executeUpdate();
						
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int updateNotice(Connection conn, Notice n) {
		PreparedStatement pstmt=null;
		int result=0;
		String sql=prop.getProperty("updateNotice");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeWriter());
			pstmt.setString(3, n.getNoticeContent());
			pstmt.setString(4, n.getFilePath());
			pstmt.setInt(5, n.getNoticeNo());
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

}
