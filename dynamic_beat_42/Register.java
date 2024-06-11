package dynamic_beat_42;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class Register extends JFrame {
    public Register() {
        setTitle("회원가입");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel registerPanel = new JPanel(new GridLayout(3, 2));
        JLabel usernameLabel = new JLabel("아이디:");
        JLabel passwordLabel = new JLabel("비밀번호:");
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton registerButton = new JButton("회원가입");

        registerPanel.add(usernameLabel);
        registerPanel.add(usernameField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(new JLabel());
        registerPanel.add(registerButton);

        add(registerPanel, BorderLayout.CENTER);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try (Connection conn = Database.getConnection();
                     PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM USERS WHERE USERNAME = ?");
                     PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO USERS (USERNAME, PASSWORD) VALUES (?, ?)")) {
                    
                    // 사용자명 중복 체크
                    checkStmt.setString(1, username);
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(null, "이미 존재하는 회원입니다!");
                    } else {
                        // 사용자명 중복이 아닐 때, 사용자 추가
                        insertStmt.setString(1, username);
                        insertStmt.setString(2, password);
                        insertStmt.executeUpdate();
                        JOptionPane.showMessageDialog(null, "회원가입 완료!");
                        dispose();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "회원가입 중 오류가 발생했습니다!");
                }
            }
        });

        setVisible(true);
    }
}