package com.javalab.database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

// 오라클 JDBC 드라이버 로딩 및 square 계정에 접속
public class Jdbc02_Connection {

	public static void main(String[] args) {
		// 오라클 JDBC 드라이버 클래스의 완전한 풀 네임
		String driver = "oracle.jdbc.driver.OracleDriver";
		/*
		 * 데이터 베이스 연결 문자열
		 */
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		// 127.0.0.1 : IP주소
		// 1521 : 포트 주소
		String dbId = "square";
		String dbPwd = "1234";
		
		// 커넥션 객체 선언(데이터 베이스에 붙기 위한 기본적인것)
		Connection con = null;
		// 커넥션 객체를 후에 닫기 위함
		
		try {
			/**
			 * Class.forName("문자열") : 문자열로 주어진 JDBC 클래스를 빌드 패스에서 찾아서 로딩
			 */
			Class.forName(driver);
			System.out.println("드라이버 로드 성공!");
			/*
			 * 커넥션 객체 얻기
			 * Class.forName("문자열")을 통해서 JDBC 드라이버를 메모리 로딩하면 자동으로 DriverManager에 등록됨.
			 */
			
			con = DriverManager.getConnection(url, dbId, dbPwd);
			System.out.println("데이터베이스 연결 성공!");
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 ERR! : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 ERR : " + e.getMessage());
		} finally {
			try {
				if (con != null) {// 커넥션 객체를 사용하고 있는지
					con.close(); // 자원 반납
				}
			} catch (SQLException e) {
				System.out.println("Connection 자원해제 ERR : "+e.getMessage());
			}
		}

	}

}
