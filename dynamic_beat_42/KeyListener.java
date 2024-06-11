package dynamic_beat_42;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {

	// 키를 눌렀을 때 어떤 키를 눌렀는지 감지하는 메소드
	@Override
	public void keyPressed(KeyEvent e) {
		// 게임이 진행되고 있지 않다면 어떠한 변화도 일어나지 않도록 키보드 변화 무력화
		if (DynamicBeat.game == null) {
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			DynamicBeat.game.pressS();
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			DynamicBeat.game.pressD();
		} else if (e.getKeyCode() == KeyEvent.VK_F) {
			DynamicBeat.game.pressF();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			DynamicBeat.game.pressSpace();
		} else if (e.getKeyCode() == KeyEvent.VK_J) {
			DynamicBeat.game.pressJ();
		} else if (e.getKeyCode() == KeyEvent.VK_K) {
			DynamicBeat.game.pressK();
		} else if (e.getKeyCode() == KeyEvent.VK_L) {
			DynamicBeat.game.pressL();
		}
	}

	// 각각의 키를 눌렀을 때 어떠한 반응을 보일지 정의하는 메소드 (키보드 떼었을 때)
	@Override
	public void keyReleased(KeyEvent e) {
		// 게임이 진행되고 있지 않다면 어떠한 변화도 일어나지 않도록 키보드 변화 무력화
		if (DynamicBeat.game == null) {
			return;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			DynamicBeat.game.releaseS();
		} else if (e.getKeyCode() == KeyEvent.VK_D) {
			DynamicBeat.game.releaseD();
		} else if (e.getKeyCode() == KeyEvent.VK_F) {
			DynamicBeat.game.releaseF();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			DynamicBeat.game.releaseSpace();
		} else if (e.getKeyCode() == KeyEvent.VK_J) {
			DynamicBeat.game.releaseJ();
		} else if (e.getKeyCode() == KeyEvent.VK_K) {
			DynamicBeat.game.releaseK();
		} else if (e.getKeyCode() == KeyEvent.VK_L) {
			DynamicBeat.game.releaseL();
		}
	}

}
