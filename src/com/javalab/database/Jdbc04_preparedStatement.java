package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// PreparedStatemnt 사용 예제
public class Jdbc04_preparedStatement {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String dbid = "square";
		String dbpwd = "1234";

		Connection con = null;
		// 쿼리문에 인자를 전달해서 SQL 구문을 실행해주는 객체
		PreparedStatement pstmt = null;
		ResultSet rs = null; // select의 결과 객체 저장

		String sql;

		try {
			Class.forName(driver);
			System.out.println("1. 드라이버 로딩 성공!");

			con = DriverManager.getConnection(url, dbid, dbpwd);
			System.out.println("2. 커넥션 객체 생성 성공!");

			// 쿼리문 인자 전달
			int bookId = 5; // bookId가 5인 도서 조회
			sql = "select * from book where book_id = ?";

			// 쿼리문에 인자를 전달해서 SQL 구문을 실행해주는 객체
			pstmt = con.prepareStatement(sql); // sql -쿼리문 전달
			pstmt.setInt(1, bookId); // 1 -> 물음표가 몇개인지 ((숫자)물음표가 몇인지, (book_id)에 숫자를 전달함)
			rs = pstmt.executeQuery();
			// executeQuery 쿼리 실행한다
			
			System.out.println("3. 조회하신 도서는?");

			while (rs.next()) {
				System.out.println(rs.getInt("book_id") + "\t" + rs.getString("book_name") + "\t" + rs.getInt("price"));
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
