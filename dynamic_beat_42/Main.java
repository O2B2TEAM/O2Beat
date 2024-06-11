package dynamic_beat_42;

import javax.swing.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    public static final int NOTE_SPEED = 3;
    public static final int SLEEP_TIME = 10;
    public static final int REACH_TIME = 2;

    public static void main(String[] args) {
        showStoryScreens();
        new Login();
    }

    private static void showStoryScreens() {
        String[] storyImages = {
            "/images/story0.png",
            "/images/story1.png",
            "/images/story2.png",
            "/images/story3.png",
            "/images/story4.png"
        };

        boolean skipPressed = false;
        for (String imagePath : storyImages) {
            StoryScreen storyScreen = new StoryScreen(imagePath);
            try {
                for (int i = 0; i < 20; i++) { // 20 x 100ms = 2 seconds
                    TimeUnit.MILLISECONDS.sleep(100);
                    if (storyScreen.isSkipPressed()) {
                        skipPressed = true;
                        break; // Skip 버튼이 눌리면 showStoryScreens 메서드를 종료하여 로그인 화면으로 이동
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            storyScreen.close();
            if (skipPressed) {
                break;
            }
        }
    }
}
