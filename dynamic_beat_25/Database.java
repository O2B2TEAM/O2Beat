package dynamic_beat_25;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	//데이터베이스 연결 자격 증명
    private static final String USER = "java";
    private static final String PASSWORD = "oracle";
    //DBMS 서버 접속
    private static final String URL = "jdbc:oracle:thin:@localhost:1522:orcl";

    // Oracle JDBC 드라이버를 로드하는 정적 블록
    static {
        try {
        	//Oracle JDBC 드라이버 클래스 로드
            Class.forName("oracle.jdbc.OracleDriver");
            System.out.println("Oracle JDBC Driver Loaded Successfully!");
        } catch (ClassNotFoundException e) {
        	//드라이버 클래스를 찾을 수 없는 경우 스택 트레이스 출력
            e.printStackTrace();
        }
    }
    // 데이터베이스 연결을 설정하고 반환하는 메소드
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
 // 데이터베이스 리소스를 닫는 메소드
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
        	// ResultSet이 null이 아니면 닫기
            if (rs != null) rs.close();
            // Statement가 null이 아니면 닫기
            if (stmt != null) stmt.close();
         // Connection이 null이 아니면 닫기
            if (conn != null) conn.close();
        } catch (SQLException e) {
        	 // 닫는 도중 SQLException이 발생하면 스택 트레이스 출력
            e.printStackTrace();
        }
    }
}

