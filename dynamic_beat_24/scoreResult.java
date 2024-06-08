package dynamic_beat_24;

import javax.swing.*;
import java.awt.*;

public class scoreResult extends Thread {
    private Image scoreResult = new ImageIcon(getClass().getResource("/images/scoreResult.png")).getImage();

    Graphics2D g;

    public scoreResult(Graphics2D g){
        this.g = g;
    }

    public void screenDraw(Graphics2D g){
        String grade=null;


        int totalScore = DynamicBeat.game.score;
//        int totalScore = 1000;
//        int totalCombo = 50;

        if(totalScore > (300*100*0.9)) {
            grade = "S";
        }else if(totalScore > (300*100*0.6)) {
            grade = "A";
        }else if(totalScore > (300*100*0.4)) {
            grade = "B";
        }else if(totalScore >= 0) {
            grade = "C";
        }
        g.drawImage(scoreResult, 0, 25, null);

        g.setFont(new Font("Arial", Font.BOLD, 70));
        g.setColor(Color.white);
        g.drawString("Score : "+String.valueOf(totalScore), 800, 290);

        g.setColor(Color.pink);
        g.drawString(grade, 950, 450);

    }

    @Override
    public void run() {
        screenDraw(g);
    }

    public void close(){
        interrupt();
    }

}