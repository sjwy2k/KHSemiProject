
//안ㄴ여핫에ㅛ!
//한지현 참여
package com.kh.common;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {

	public static Connection getConnection() {
		Connection conn=null;
		try {
			Properties prop=new Properties();
			String path=JDBCTemplate.class.getResource("/sql/driver/driver.properties").getPath();
			prop.load(new FileReader(path));
			Class.forName(prop.getProperty("driver"));
			conn=DriverManager.getConnection(prop.getProperty("url"),prop.getProperty("user"),prop.getProperty("pw"));
			conn.setAutoCommit(false);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	//각 객체 종료 메서드 만들기
	//conn.close
	public static void close(Connection conn) {
		try{
			if(conn!=null&&!conn.isClosed()) {
				conn.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//stmt.close
	public static void close(Statement stmt) {
		try{
			if(stmt!=null&&!stmt.isClosed()) {
				stmt.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//rs.close
	public static void close(ResultSet rs) {
		try{
			if(rs!=null&&!rs.isClosed()) {
				rs.close();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void commit(Connection conn) {
		try {
			if(conn!=null &&!conn.isClosed()) {
				conn.commit();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn!=null&&!conn.isClosed()) {
				conn.rollback();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
