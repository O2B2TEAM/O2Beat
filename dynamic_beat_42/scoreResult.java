package dynamic_beat_42;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class scoreResult extends Thread {
    private Image scoreResult = new ImageIcon(getClass().getResource("/images/scoreResult.png")).getImage();

    private Graphics2D g;
    private String username;
    private List<Integer> topScores;

    public scoreResult(Graphics2D g, String username){
        this.g = g;
        this.username=username;
        this.topScores = Database.getTopScores(username, 3); // 상위 3개의 점수를 가져옴
    }
    
    public void screenDraw(Graphics2D g){
        String grade=null;

        int totalScore = DynamicBeat.game.score;

        if(totalScore > (5000)) {
            grade = "S";
        }else if(totalScore > (4000)) {
            grade = "A";
        }else if(totalScore > (3000)) {
            grade = "B";
        }else if(totalScore >= 0) {
            grade = "C";
        }
        g.drawImage(scoreResult, 0, 25, null);

        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.white);
        g.drawString("Score : "+String.valueOf(totalScore), 800, 330);
        //g.drawString("Ranking", 800, 430);
        
        // 상위 3개의 점수를 표시
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.setColor(Color.white);
        for (int i = 0; i < topScores.size(); i++) {
            g.drawString((i + 1) + "st : " + topScores.get(i), 800, 450 + (i * 50));
        }

        g.setFont(new Font("Arial", Font.BOLD, 200));
        g.setColor(Color.pink);
        g.drawString(grade, 950, 230);
        

        // 점수 저장
        Database.saveScore(username, totalScore);

    }

    @Override
    public void run() {
        screenDraw(g);
    }

    public void close(){
        interrupt();
    }

}