package dynamic_beat_42;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Note extends Thread {
	
	private Image noteBasicImage = new ImageIcon(Main.class.getResource("/images/noteBasic.png")).getImage();
	private int x,y=580-(1000/Main.SLEEP_TIME*Main.NOTE_SPEED)*Main.REACH_TIME; //노트 현재 위치 좌표, 현재 y좌표 시작 위치는 -120
	private String noteType; //
	private boolean proceeded=true; //현재 노트의 진행 여부 체크
	
	//현재 노트의 타입을 알아낼 수 있도록 반환해주는 함수
	public String getNoteType() {
		return noteType;
	}
	
	//현재 노트의 진행 여부를 get 함수
	public boolean isProceeded() {
		return proceeded;
	}
	
	//현재 노트가 움직이지 않도록 하는 (노트가 성공적으로 입력되었을 때 더이상 움직이지 않도록)
	public void close() {
		proceeded=false;
	}
	
	//생성자
	public Note(String noteType) {
		if(noteType.equals("S")) {
			x=228;
		}else if(noteType.equals("D")) {
			x=332;
		}else if(noteType.equals("F")) {
			x=436;
		}else if(noteType.equals("Space")) {
			x=540;
		}else if(noteType.equals("J")) {
			x=744;
		}else if(noteType.equals("K")) {
			x=848;
		}else if(noteType.equals("L")) {
			x=952;
		}
		this.noteType=noteType; //초기화
	}
	
	public void screenDraw(Graphics2D g) {
		if(!noteType.equals("Space")) { //스페이스바
			g.drawImage(noteBasicImage,x,y,null);
		}else {
			g.drawImage(noteBasicImage,x,y,null);
			g.drawImage(noteBasicImage,x+100,y,null);
		}
	}
	//노트 떨어지는 함수
	public void drop() {
		y+=Main.NOTE_SPEED; //7만큼 y좌표가 증가(아래쪽으로) 떨어짐
		if(y>=620) { //노트가 판정바를 벗어나는 지점 
			System.out.println("Miss");
			close();
		}
	}
	
	@Override
	public void run() {
		try {
			while(true) {
				drop();
				if(proceeded) { //노트가 내려오고 있을때는 내려가기
					Thread.sleep(Main.SLEEP_TIME); //1=0.001초, 1초에 700px 만큼 y좌표가 아래로 내려감	
				}else { //노트가 판정되거나 다 내려가고 나서는 노트 멈추기
					interrupt();
					break;
				}
			}
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
	}
	//판정함수
	public String judge() {
		if(y>=613) {
			System.out.println("Late");
			close();
			return "Late";
		}else if(y>=600) {
			System.out.println("Good");
			close();
			return "Good";
		}else if(y>=587) {
			System.out.println("Great");
			close();
			return "Great";
		}else if(y>=573) {
			System.out.println("Perfect");
			close();
			return "Perfect";
		}else if(y>=565) {
			System.out.println("Great");
			close();
			return "Great";
		}else if(y>=550) {
			System.out.println("Good");
			close();
			return "Good";
		}else if(y>=535) {
			System.out.println("Early");
			close();
			return "Early";
		}
		return "Miss";
	}
	public int getY() {
		return y;
	}
}