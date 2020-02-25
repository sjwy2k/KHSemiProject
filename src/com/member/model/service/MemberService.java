package com.member.model.service;

import java.sql.Connection;

import com.member.model.dao.MemberDao;
import com.member.model.vo.Member;
import static kh.common.JDBCTemplate.getConnection;
import static kh.common.JDBCTemplate.close;
import static kh.common.JDBCTemplate.commit;
import static kh.common.JDBCTemplate.rollback;

public class MemberService {

	private MemberDao dao=new MemberDao();

	public Member selectId(String id, String pw) {
		
		Connection conn=getConnection();
		Member m=dao.selectId(conn,id,pw);
		
		close(conn);		
		return m;
	}

	public int insertMember(Member m) {
		Connection conn=getConnection();
		int result=dao.insertMember(conn,m);
		
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}

	public boolean selectCheckId(String userId) {

		Connection conn=getConnection();
		boolean flag=dao.selectCheckId(conn,userId);
		close(conn);
		return flag;
	}

//	public Member getMemberInfo(String userId) {
//		Connection conn=getConnection();
//		Member m=dao.getMemberInfo(conn,userId);
//		return m;
//	}

	public Member selectSearch(String userId) {

		Connection conn=getConnection();
		Member m=dao.searchMember(conn, userId);
		return m;
	}

	public int updateMember(Member m) {
				
		Connection conn=getConnection();
		int result=dao.updateMember(conn,m);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int deleteMember(String userId) {

		Connection conn=getConnection();
		int result=dao.deleteMember(conn,userId);
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}


}
