package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 주문테이블과 도서테이블을 조인해서 주문정보 + 도서정보가 나오도록 조회하시오
public class Jdbc12_select_join01 {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String dbId = "square";
		String dbPwd = "1234";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql;
		
		try {
			// 드라이버 로딩
			Class.forName(driver);
			System.out.println("드라이버 로딩 성공");
			
			con = DriverManager.getConnection(url, dbId, dbPwd);
			System.out.println("커넥션 객체 생성 성공");
			
			sql = "SELECT O.ORDER_ID, O.CUST_ID, O.BOOK_ID, ";
			sql += " B.BOOK_NAME, O.ORDER_DATE, B.PUBLISHER, B.PRICE";
			sql += " FROM BOOK B, ORDERS O";
			sql += " WHERE B.BOOK_ID = O.BOOK_ID";
			sql += " ORDER BY O.ORDER_ID ASC";
		
			// 쿼리문에 인자를 전달해서 SQL구문을 실행해주는 객체
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				System.out.println(rs.getInt("ORDER_ID")+"\t"
								   +rs.getInt("CUST_ID")+"\t"
								   +rs.getInt("BOOK_ID")+"\t"
								   +rs.getString("BOOK_NAME")+"\t"
								   +rs.getDate("ORDER_DATE")+"\t"
								   +rs.getString("PUBLISHER")+"\t"
								   +rs.getInt("PRICE"));
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
