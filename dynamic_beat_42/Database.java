package dynamic_beat_42;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    // 점수를 데이터베이스에 저장하는 메소드
    public static void saveScore(String username, int score) {
        String checkQuery = "SELECT COUNT(*) FROM SCORES WHERE USERNAME = ? AND SCORE = ?";
        String insertQuery = "INSERT INTO SCORES (USERNAME, SCORE) VALUES (?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // 기존 점수가 있는지 확인
            checkStmt.setString(1, username);
            checkStmt.setInt(2, score);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                // 기존 점수가 없을 때만 새 점수 저장
                insertStmt.setString(1, username);
                insertStmt.setInt(2, score);
                insertStmt.executeUpdate();
                System.out.println("Score saved successfully!");
            } else {
                System.out.println("Duplicate score found, not saved.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // 최고 점수를 가져오는 메소드
    public static int getHighScore(String username) {
        String query = "SELECT MAX(SCORE) AS HIGHSCORE FROM SCORES WHERE USERNAME = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("HIGHSCORE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // 기본값으로 0 반환
    }
    public static List<Integer> getTopScores(String username, int limit) {
        List<Integer> topScores = new ArrayList<>();
        String query = "SELECT SCORE FROM SCORES WHERE USERNAME = ? ORDER BY SCORE DESC FETCH FIRST ? ROWS ONLY";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setInt(2, limit);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                topScores.add(rs.getInt("SCORE"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topScores;
    }
}