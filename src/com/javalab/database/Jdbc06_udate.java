package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * PreparedStatement을 사용한 수정
 */
public class Jdbc06_udate {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String dbid = "square";
		String dbpwd = "1234";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int resultNo = 0;
		String sql; // SQL문을 저장할 변수 선언

		try {
			Class.forName(driver);
			System.out.println("드라이버 로드 성공!");

			con = DriverManager.getConnection(url, dbid, dbpwd);
			System.out.println("데이터베이스 연결 성공!");

			// update query
			int bookId = 8;
			String bookName = "세이노의 가르침";
			sql = "update book set book_name = ? where book_id = ?";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, bookName);
			pstmt.setInt(2, bookId);

			resultNo = pstmt.executeUpdate();

			if (resultNo > 0) {
				System.out.println("수정 성공!");
			} else {
				System.out.println("수정 실패!");
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
