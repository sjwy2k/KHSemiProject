package com.member.model.service;

import java.sql.Connection;

import com.member.model.dao.MemberDao;
import com.member.model.vo.Member;
import static kh.common.JDBCTemplate.getConnection;
import static kh.common.JDBCTemplate.close;

public class MemberService {

	private MemberDao dao=new MemberDao();

	public Member selectId(String id, String pw) {
		
		Connection conn=getConnection();
		Member m=dao.selectId(conn,id,pw);
		
		close(conn);		
		return m;
	}
}
