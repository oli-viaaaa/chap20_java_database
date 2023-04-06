package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 문제. 역도 관련 책 중에서 10,000원이 넘는 책을 출력하라
public class Jdbc11_select_cond {
	public static void main(String[] args) {
		// 오라클 드라이버 로딩 문자열
		String driver = "oracle.jdbc.driver.OracleDriver";
		// 데이터 베이스 연결 문자열
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		// 데이터베이스 계정명
		String dbId = "square";
		// 데이터베이스 비밀번호
		String dbPwd = "1234";
		
		// 데이터베이스 연결하는 다리(bridge)와 같다
		Connection con = null;
		
		// 쿼리문에 인자를 전달햇서 SQL 구문을 실행시켜주는 객체
		PreparedStatement pstmt = null;
		
		// 실행된 결과를 받아오는 객체
		ResultSet rs = null;
		
		String sql;
		
		try {
			// 드라이버 로딩
			Class.forName(driver);
			System.out.println("드라이버 로딩 성공");
			
			con = DriverManager.getConnection(url, dbId, dbPwd);
			System.out.println("커넥션 객체 생성 성공");
			
			String title = "역도";
			int price = 10000;
			
			sql = "SELECT B.BOOK_ID, B.BOOK_NAME, B.PUBLISHER, B.PRICE";
			sql += " FROM BOOK B ";
			sql += " WHERE B.BOOK_NAME LIKE Concat('%'|| ?,'%')";
			sql += " AND B.PRICE >= ? ";
			 
			
			// 쿼리문에 인자를 전달해서 SQL 구문을실행해주는 객체
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setInt(2, price);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getInt("BOOK_ID") + "\t" 
								 + rs.getString("BOOK_NAME") + "\t" 
								 + rs.getString("PUBLISHER") + "\t"
								 + rs.getInt("price"));
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 ERR! : " + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQL ERR : " + e.getMessage());
		} finally {
			try { // 자원 해제(반납) 순서는 작은거에서 큰걸로 가야함
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.out.println("자원해제 ERR! : " + e.getMessage());
			}
		}
	}
}
