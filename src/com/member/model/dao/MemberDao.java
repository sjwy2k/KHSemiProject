package com.member.model.dao;

import static kh.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.member.model.vo.Member;

public class MemberDao {
	
	private Properties prop=new Properties();
	
	public MemberDao() {
		try {
			String path=MemberDao.class.getResource("/sql/member/member-query.properties").getPath();
			prop.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//멤버 로그인 로직처리
	public Member selectId(Connection conn, String id, String pw) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=prop.getProperty("selectId");
		Member m=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				m=new Member();
				m.setUserId(rs.getString("userId"));
				m.setPassword(rs.getString("password"));
				m.setUsername(rs.getString("username"));
				m.setGender(rs.getString("gender"));
				m.setAge(rs.getInt("age"));
				m.setEmail(rs.getString("email"));
				m.setPhone(rs.getString("phone"));
				m.setAddress(rs.getString("address"));
				m.setHobby(rs.getString("hobby"));
				m.setEnrollDate(rs.getDate("enrollDate"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return m;
	}

	public int insertMember(Connection conn, Member m) {
		PreparedStatement pstmt=null;
		
		int result=0;
		String sql=prop.getProperty("insertMember");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getPassword());
			pstmt.setString(3, m.getUsername());
			pstmt.setString(4, m.getGender());
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, m.getHobby());			
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public boolean selectCheckId(Connection conn, String userId) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=prop.getProperty("selectCheckId");
		boolean flag=true; //아이디 없다
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				//조회된 것이 있으면 아이디를 사용할 수 없다
				flag=false; //아이디 있다
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return flag;
	}

	public Member getMemberInfo(Connection conn, String userId) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=prop.getProperty("selectCheckId");
		Member m=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				m=new Member();
				m.setUserId(rs.getString("userId"));
				m.setPassword(rs.getString("password"));
				m.setAge(rs.getInt("age"));
				m.setGender(rs.getString("gender"));
				m.setAddress(rs.getString("address"));
				m.setEmail(rs.getString("email"));
				m.setHobby(rs.getString("hobby"));
				m.setPhone(rs.getString("phone"));
				m.setUsername(rs.getString("username"));
				m.setEnrollDate(rs.getDate("enrollDate"));	
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		
		return m;
	}

	public Member searchMember(Connection conn, String userId) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql=prop.getProperty("selectCheckId");
		Member m=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				m=new Member();
				m.setUserId(rs.getString("userId"));
				m.setPassword(rs.getString("password"));
				m.setUsername(rs.getString("username"));
				m.setGender(rs.getString("gender"));
				m.setAge(rs.getInt("age"));
				m.setEmail(rs.getString("email"));
				m.setPhone(rs.getString("phone"));
				m.setAddress(rs.getString("address"));
				m.setHobby(rs.getString("hobby"));
				m.setEnrollDate(rs.getDate("enrollDate"));	
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}		
		
		return m;
	}

	public int updateMember(Connection conn, Member m) {

		PreparedStatement pstmt=null;
		String sql=prop.getProperty("updateMember");
		int result=0;
		
		try {
			pstmt=conn.prepareStatement(sql);
//			pstmt.setString(1, m.getPassword());
			pstmt.setString(1, m.getUsername());
			pstmt.setString(2, m.getGender());
			pstmt.setInt(3, m.getAge());
			pstmt.setString(4, m.getEmail());
			pstmt.setString(5, m.getPhone());
			pstmt.setString(6, m.getAddress());
			pstmt.setString(7, m.getHobby());
			pstmt.setString(8, m.getUserId());
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteMember(Connection conn, String userId) {
		PreparedStatement pstmt=null;
		String sql=prop.getProperty("deleteMember");
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int updatePassword(Connection conn, String userId, String pw) {
		PreparedStatement pstmt=null;
		String sql=prop.getProperty("updatePassword");
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,pw);			
			pstmt.setString(2,userId);
			result=pstmt.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(conn);
		}
		return result;
	}

}
