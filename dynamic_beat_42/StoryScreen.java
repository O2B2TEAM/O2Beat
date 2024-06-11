package dynamic_beat_42;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoryScreen extends JFrame {

    private Image storyImage;
    private boolean skipPressed = false;

    public StoryScreen(String imagePath) {
        storyImage = new ImageIcon(Main.class.getResource(imagePath)).getImage();
        setUndecorated(true);
        setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Absolute layout for positioning the skip button

        JButton skipButton = new JButton("Skip");
        skipButton.setBounds(1150, 20, 100, 40); // Positioning the skip button
        skipButton.setBackground(Color.BLACK);
        skipButton.setForeground(Color.WHITE);
        skipButton.setFocusPainted(false);
        skipButton.setBorderPainted(false);
        skipButton.setFont(new Font("Arial", Font.BOLD, 20));
        skipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                skipPressed = true;
                close();
                new Login(); // 스킵 버튼을 누르면 바로 로그인 화면으로 이동
            }
        });
        add(skipButton);

        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // Ensure the skip button is drawn
        g.drawImage(storyImage, 0, 0, getWidth(), getHeight(), this); // Draw the story image
    }

    public boolean isSkipPressed() {
        return skipPressed;
    }

    public void close() {
        setVisible(false);
        dispose();
    }
}
