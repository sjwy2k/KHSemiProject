package com.kh.admin.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.member.model.vo.Member;

public class AdminDao {

	private Properties prop=new Properties();
	
	public AdminDao() {
		try {
			String path=AdminDao.class.getResource("/sql/admin/admin-query.properties").getPath();
			prop.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

//	public List<Member> selectAllMember(Connection conn) {
//
//		PreparedStatement pstmt=null;
//		ResultSet rs=null;
//		String sql=prop.getProperty("selectAllMember");
//		List<Member> members=new ArrayList<Member>();
//		Member m=null;
//		try {
//			pstmt=conn.prepareStatement(sql);
//			rs=pstmt.executeQuery();
//			while(rs.next()) {
//				m=new Member();
//				m.setUserId(rs.getString("userId"));
//				m.setPassword(rs.getString("password"));
//				m.setUsername(rs.getString("username"));
//				m.setGender(rs.getString("gender"));
//				m.setAge(rs.getInt("age"));
//				m.setEmail(rs.getString("email"));
//				m.setPhone(rs.getString("phone"));
//				m.setAddress(rs.getString("address"));
//				m.setHobby(rs.getString("hobby"));
//				m.setEnrollDate(rs.getDate("enrollDate"));
//				members.add(m);
//			}
//		}catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			close(rs);
//			close(pstmt);
//		}
//		return members;
//	}

	public List<Member> searchMember(Connection conn) {

		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List<Member> list=new ArrayList<Member>();
		String sql=prop.getProperty("searchMember");
		Member m=null;
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				m=new Member();
				m.setUserId(rs.getString("userId"));
				m.setUsername(rs.getString("username"));
				m.setGender(rs.getString("gender"));
				m.setAge(rs.getInt("age"));
				m.setEmail(rs.getString("email"));
				m.setPhone(rs.getString("phone"));
				m.setAddress(rs.getString("address"));
				m.setHobby(rs.getString("hobby"));
				m.setEnrollDate(rs.getDate("enrollDate"));
				list.add(m);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return list;
	}

//	public List<Member> searchMemberForId(Connection conn, String searchKeyword) {
//		PreparedStatement pstmt=null;
//		ResultSet rs=null;
//		List<Member> list=new ArrayList<Member>();
//		String sql=prop.getProperty("searchMemberForId");
//		Member m=null;
//		try {
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setString(1, searchKeyword);
//			rs=pstmt.executeQuery();
//			while(rs.next()) {
//				m=new Member();
//				m.setUserId(rs.getString("userId"));
//				m.setUsername(rs.getString("username"));
//				m.setGender(rs.getString("gender"));
//				m.setAge(rs.getInt("age"));
//				m.setEmail(rs.getString("email"));
//				m.setPhone(rs.getString("phone"));
//				m.setAddress(rs.getString("address"));
//				m.setHobby(rs.getString("hobby"));
//				m.setEnrollDate(rs.getDate("enrollDate"));
//				list.add(m);
//			}
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}finally {
//			close(rs);
//			close(pstmt);
//		}
//		return list;
//	}

	public List<Member> searchMember(Connection conn, String searchType, String searchKeyword) {
//		PreparedStatement pstmt=null;
		Statement stmt=null;
		ResultSet rs=null;
//		String sql=prop.getProperty("searchMemberKey");
//		switch(searchType) {
//			case "userId" : sql=prop.getProperty("searchMemberKey");
//			case "userName" : sql=prop.getProperty("searchMemberKey2");
//			case "gender" : sql=prop.getProperty("searchMemberKey3");
//		}
		//쿼리문의 컬럼을 여러가지 조건으로 구하고 싶을 경우 PreparedStatement가 아닌 
		//Statement를 사용하는 것이 더 편할 수도 있다.
		//동적으로 쿼리문의 요소를 변경해 검색 처리
		//오타주의!
//		String sql="SELECT * FROM MEMBER WHERE "+searchType+" LIKE '%'"+searchKeyword+"'%'";
		String sql="SELECT * FROM MEMBER WHERE "+searchType+" like '%"+searchKeyword+"%'";
		List<Member> list=new ArrayList<Member>();
		Member m=null;
		try {
//			pstmt=conn.prepareStatement(sql);
//			pstmt.setString(1, searchKeyword);
//			rs=pstmt.executeQuery();
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				m=new Member();
				m.setUserId(rs.getString("userId"));
				m.setUsername(rs.getString("username"));
				m.setGender(rs.getString("gender"));
				m.setAge(rs.getInt("age"));
				m.setEmail(rs.getString("email"));
				m.setPhone(rs.getString("phone"));
				m.setAddress(rs.getString("address"));
				m.setHobby(rs.getString("hobby"));
				m.setEnrollDate(rs.getDate("enrollDate"));
				list.add(m);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
//			close(pstmt);
			close(stmt);
		}
		return list;
	}

	public List<Member> searchMember(Connection conn, int cPage, int numPerPage) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=prop.getProperty("searchPageMember");
		List<Member> list=new ArrayList<Member>();
		Member m=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, (cPage-1)*numPerPage+1); //시작 데이터 번호 -> rnum기준
			//cPage=1, 시작번호 : 1
			//cPage=2, 시작번호 : 11
			//cPage=3, 시작번호 : 21
			pstmt.setInt(2, cPage*numPerPage); //끝 데이터 번호 -> rnum기준
			//cPage=1, 끝번호 : 10
			//cPage=2, 끝번호 : 20
			//cPage=3, 끝번호 : 30
			
			rs=pstmt.executeQuery();
			while(rs.next()) {
				m=new Member();
				m.setUserId(rs.getString("userId"));
				m.setUsername(rs.getString("username"));
				m.setGender(rs.getString("gender"));
				m.setAge(rs.getInt("age"));
				m.setEmail(rs.getString("email"));
				m.setPhone(rs.getString("phone"));
				m.setAddress(rs.getString("address"));
				m.setHobby(rs.getString("hobby"));
				m.setEnrollDate(rs.getDate("enrollDate"));
				list.add(m);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
//			close(stmt);
		}
		return list;
	}


	public List<Member> searchMember(Connection conn, String searchType, String searchKeyword, int cPage,
			int numPerPage) {
		Statement stmt=null;
		ResultSet rs=null;
//		String sql=prop.getProperty("searchPageMember2");
		String sql=
		"SELECT * FROM "
		+ "(SELECT ROWNUM AS RNUM, A.* FROM "
			+ "(SELECT * FROM MEMBER WHERE "+searchType+" like '%"+searchKeyword+"%' ORDER BY ENROLLDATE)"
		+ " A) "
		+ "WHERE RNUM BETWEEN "+((cPage-1)*numPerPage+1)+" AND "+cPage*numPerPage;
		

		List<Member> list=new ArrayList<Member>();
		Member m=null;
		try {
			stmt=conn.createStatement();
//			stmt.setInt(1, (cPage-1)*numPerPage+1); //시작 데이터 번호 -> rnum기준
			//cPage=1, 시작번호 : 1
			//cPage=2, 시작번호 : 11
			//cPage=3, 시작번호 : 21
//			stmt.setInt(2, cPage*numPerPage); //끝 데이터 번호 -> rnum기준
			//cPage=1, 끝번호 : 10
			//cPage=2, 끝번호 : 20
			//cPage=3, 끝번호 : 30
			
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				m=new Member();
				m.setUserId(rs.getString("userId"));
				m.setUsername(rs.getString("username"));
				m.setGender(rs.getString("gender"));
				m.setAge(rs.getInt("age"));
				m.setEmail(rs.getString("email"));
				m.setPhone(rs.getString("phone"));
				m.setAddress(rs.getString("address"));
				m.setHobby(rs.getString("hobby"));
				m.setEnrollDate(rs.getDate("enrollDate"));
				list.add(m);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
//			close(pstmt);
			close(stmt);
		}
		return list;
	}
	
	public int memberCount(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=prop.getProperty("memberCount");
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
//				count++;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

//	public int memberCount2(Connection conn, String searchType, String searchKeyword) {
//		PreparedStatement pstmt=null;
//		ResultSet rs=null;
////		String sql=prop.getProperty("memberCount2");
//		String sql="SELECT COUNT(*) FROM MEMBER WHERE "+searchType+" like '%"+searchKeyword+"%'";
//
//		int result=0;
//		try {
//			pstmt=conn.prepareStatement(sql);
//			rs=pstmt.executeQuery();
//			if(rs.next()) {
//				result=rs.getInt(1);
////				count++;
//			}
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}finally {
//			close(rs);
//			close(pstmt);
//		}
//		return result;
//	}

	public List<Member> searchMemberPage(Connection conn, int cPage, int numPerPage, String searchType,
			String searchKeyword) {
		
		Statement stmt=null;
		ResultSet rs=null;
		List<Member> list=new ArrayList();
		Member m=null;
		String sql="SELECT * FROM "
					+ "(SELECT ROWNUM AS RNUM, A.* FROM "
						+ "(SELECT * FROM MEMBER WHERE "+searchType+" LIKE '%"+searchKeyword+"%' ORDER BY ENROLLDATE) "
					+ "A) "
					+ "WHERE RNUM BETWEEN "+((cPage-1)*numPerPage+1)+" AND "+(cPage*numPerPage);
		
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				m=new Member();
				m.setUserId(rs.getString("userId"));
				m.setUsername(rs.getString("username"));
				m.setGender(rs.getString("gender"));
				m.setAge(rs.getInt("age"));
				m.setEmail(rs.getString("email"));
				m.setPhone(rs.getString("phone"));
				m.setAddress(rs.getString("address"));
				m.setHobby(rs.getString("hobby"));
				m.setEnrollDate(rs.getDate("enrollDate"));
				list.add(m);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(stmt);
		}
		return list;
	}

	public int memberCount(Connection conn, String searchType, String searchKeyword) {

		Statement stmt=null;
		ResultSet rs=null;
		int result=0;
		String sql="SELECT COUNT(*) AS cnt FROM MEMBER WHERE "+searchType+" LIKE '%"+searchKeyword+"%'";
		
		try {
			stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()) result=rs.getInt("cnt");
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(stmt);
		}
		return result;
	}
}
