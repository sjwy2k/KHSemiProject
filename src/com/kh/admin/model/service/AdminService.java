package com.kh.admin.model.service;

import static com.kh.common.JDBCTemplate.close;
import static com.kh.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import com.kh.admin.model.dao.AdminDao;
import com.member.model.vo.Member;

public class AdminService {

	private AdminDao dao=new AdminDao();
	
//	public List<Member> selectAllMember(){
//		Connection conn=getConnection();
//		List<Member> members=dao.selectAllMember(conn);
//		close(conn);
//		return members;
//	}

	public List<Member> searchMember() {

		//connection관리
		Connection conn=getConnection();
		List<Member> list=dao.searchMember(conn);
		close(conn);
		return list;
	}

//	public List<Member> searchMemberForId(String searchKeyword) {
//		Connection conn=getConnection();
//		List<Member> list=dao.searchMemberForId(conn, searchKeyword);
//		close(conn);
//		return list;
//	}

	public List<Member> searchMember(String searchType, String searchKeyword) {
		Connection conn=getConnection();
		List<Member> list=dao.searchMember(conn, searchType, searchKeyword);
		close(conn);
		return list;
	}

	public List<Member> searchMember(int cPage, int numPerPage) {
		Connection conn=getConnection();
		List<Member> list=dao.searchMember(conn, cPage, numPerPage);
		close(conn);
		return list;
	}
	
	public List<Member> searchMember(String searchType, String searchKeyword, int cPage, int numPerPage) {
		Connection conn=getConnection();
		List<Member> list=dao.searchMember(conn, searchType, searchKeyword, cPage, numPerPage);
		close(conn);
		return list;
	}

	public int memberCount() {
		Connection conn=getConnection();
		int result=dao.memberCount(conn);
		close(conn);
		return result;
	}

	public List<Member> searchMemberPage(int cPage, int numPerPage, String searchType, String searchKeyword) {
		Connection conn=getConnection();
		List<Member> list=dao.searchMemberPage(conn, cPage, numPerPage, searchType, searchKeyword);
		close(conn);
		return list;
	}
	
	public int memberCount(String searchType, String searchKeyword) {
		Connection conn=getConnection();
		int result=dao.memberCount(conn, searchType, searchKeyword);
		close(conn);
		return result;
	}
}
