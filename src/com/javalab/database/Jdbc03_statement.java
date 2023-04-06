package com.javalab.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 오라클 드라이버 로딩 및 BOOK 테이블이 있는 계정에 접속 후 Statement 객체
 */
public class Jdbc03_statement {

	public static void main(String[] args) {
		// 오라클 드라이버 로딩 문자열
		String driver = "oracle.jdbc.driver.OracleDriver";
		// 데이터 베이스 연결 문자열
		String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		// 데이터베이스 계정명
		String dbId = "square";
		// 데이터베이스 비밀번호
		String dbPwd = "1234";

		// 데이터베이스 연결 객체
		Connection con = null;

		// 커넥션 객체를 통해서 데이터베이스에 쿼리(SQL)를 실행해주는 객체
		Statement stmt = null;

		// 실행된 쿼리문의 결과를 반환받는 객체
		ResultSet rs = null;

		try {

			// 1. 드라이버 로딩
			Class.forName(driver);
			System.out.println("1. 드라이버 로딩 성공!");

			// 2. 데이터베이스 커넥션(연결)
			con = DriverManager.getConnection(url, dbId, dbPwd);
			System.out.println("2. 커넥션 객체 생성 성공!");

			// 3. 커넥션 객체를 통해서 데이터베이스에 쿼리(SQL)를 실행해주는 Statement 객체 얻음
			stmt = con.createStatement();
			System.out.println("3. stmt 객체 생성 성공  : " + stmt);

			// 4. 생성한 Statment 객체를 통해서 쿼리하기 위한 SQL 쿼리 문장 만들기(삽입, 수정, 삭제, 조회)
			String sql = "select * from book";

			// 5. Statment 객체의 executeQuery() 메소드를 통해서 쿼리 실행
			// 데이터 베이스에서 조회된 결과가 ResultSet 객체에 담겨옴
			rs = stmt.executeQuery(sql);
			System.out.println("5. sql 명령어 성공적으로 실행됨!! 결과 존재? :" + rs.next()); // rs.next()로 첫번째 데이터 출력 안됨 대신 true 출력
			System.out.println();

			// 6. rs.next()의 의미 설명
			while (rs.next()) { // 결과를 가져올 때 보통 while문 사용
				// next타입 boolean으로 더 이상 가져올 것이 없으면 false
				System.out.println(rs.getInt("book_id") + "\t" + rs.getString("book_name") + "\t" + rs.getInt("price"));
			} 

		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 ERR! :" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("SQL ERR : " +e.getMessage());
		} finally {
			try { // 자원 해제(반납) 순서는 작은거에서 큰걸로 가야함
				if(rs != null) {
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
