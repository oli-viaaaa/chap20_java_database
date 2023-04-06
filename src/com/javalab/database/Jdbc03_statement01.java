package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Jdbc03_statement01 {
	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		String dbid = "square";
		String dbpwd = "1234";

		// 데이터 베이스 연결 객체
		Connection con = null;

		// SQL 실행 객체
		Statement stmt = null;

		// 결과 반환 객체
		ResultSet rs = null;

		try {
			// 드라이버 로딩
			Class.forName(driver);
			System.out.println("1. 드라이버 로딩 성공!");

			// 데이터 베이스연결
			con = DriverManager.getConnection(url, dbid, dbpwd);
			System.out.println("2. 커넥션 객체 생성 성공!");

			stmt = con.createStatement();
			System.out.println("3. stmt 객체 생성 성공 : " + stmt);

			String sql = "select * from orders";
			// 나열하고 싶은 항목만 나열 가능) select order_id, cust_id, book_id from orders;

			rs = stmt.executeQuery(sql);
			System.out.println("5. sql 명령어 성공적으로 실행됨! 결과 존재? : " + rs.next());
			System.out.println();

			while (rs.next()) { // 가져오고 싶은 항목은 위에서 선언해줘야 가져올 수 있음
				System.out.println(rs.getInt("order_id") + "\t" 
								+ rs.getInt("cust_id") + "\t" 
								+ rs.getInt("book_id")+ "\t" 
								+ rs.getInt("saleprice") + "\t"
								+ rs.getDate("order_date"));
			}

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 ERR! :" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQL ERR : " + e.getMessage());
		} finally {
			try { // 자원 해제(반납) 순서는 작은거에서 큰걸로 가야함
				if (rs != null) {
					rs.close();
				}
				if (stmt != null) {
					stmt.close();
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
