package dynamic_beat_42;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import java.util.HashMap;

public class Login extends JFrame {
	// 사용자 점수를 저장할 해시맵

    private HashMap<String, Integer> scores = new HashMap<>();

    public Login() {
    	 // 프레임 설정
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

     // 로그인 패널 설정
        JPanel loginPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("아이디:");
        JLabel passwordLabel = new JLabel("비밀번호:");
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("로그인");
        JButton registerButton = new JButton("회원가입");

        // 로그인 패널에 컴포넌트 추가

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);
        // 로그인 패널을 프레임에 추가
        add(loginPanel, BorderLayout.CENTER);
        // 로그인 버튼에 액션 리스너 추가
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                // 데이터베이스 연결 및 사용자 인증
                try (Connection conn = Database.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement("SELECT PASSWORD FROM USERS WHERE USERNAME = ?")) {
                    pstmt.setString(1, username);
                    ResultSet rs = pstmt.executeQuery();
                    // 비밀번호 확인
                    if (rs.next() && rs.getString("PASSWORD").equals(password)) {
                        // 인증 성공 시 DynamicBeat 화면으로 전환
                    	new DynamicBeat(username, scores);
                        dispose();// 로그인 창 닫기
                    } else {
                    	 // 인증 실패 시 메시지 박스 표시
                        JOptionPane.showMessageDialog(null, "다시 시도해주세요!");
                    }
                } catch (SQLException ex) {
                	 // SQL 예외 발생 시 스택 트레이스 출력
                    ex.printStackTrace();
                }
            }
        });
        // 회원가입 버튼에 액션 리스너 추가
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register();// 회원가입 창 열기
            }
        });

        setVisible(true); // 로그인 창을 보이도록 설정
    }
}
