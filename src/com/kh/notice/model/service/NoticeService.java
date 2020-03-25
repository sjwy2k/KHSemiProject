package com.kh.notice.model.service;

import static com.kh.common.JDBCTemplate.getConnection;
import static com.kh.common.JDBCTemplate.close;
import static com.kh.common.JDBCTemplate.commit;
import static com.kh.common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.kh.notice.model.dao.NoticeDao;
import com.kh.notice.model.vo.Notice;

public class NoticeService {

	private NoticeDao dao=new NoticeDao();

//	public List<Notice> searchNotice(int cNoticePage, int noticeNumPerPage) {
//		Connection conn=getConnection();
//		List<Notice> list=dao.searchNotice(conn, cNoticePage, noticeNumPerPage);
//		close(conn);
//		return list;
//	}
	public List<Notice> searchNotice(int cPage, int numPerPage) {
		Connection conn=getConnection();
		List<Notice> list=dao.searchNotice(conn, cPage, numPerPage);
		close(conn);
		return list;
	}

	public int noticeCount() {
		Connection conn=getConnection();
		int result=dao.noticeCount(conn);
		close(conn);
		return result;
	}

//	public Notice searchNoticeForNo(int no) {
//		Connection conn=getConnection();
//		Notice n=dao.searchNoticeForNo(conn, no);
//		close(conn);
//		return n;
//	}

	public Notice selectNotice(int no) {
		Connection conn=getConnection();
		Notice n=dao.selectNotice(conn, no);
		close(conn);
		return n;
	}

	public int insertNotice(Notice n) {
		Connection conn=getConnection();
		int result=dao.insertNotice(conn, n);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public int updateNotice(Notice n) {
		Connection conn=getConnection();
		int result=dao.updateNotice(conn, n);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
}
