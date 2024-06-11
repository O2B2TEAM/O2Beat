package dynamic_beat_42;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player; //jlayer

public class Music extends Thread{ //thread: 프로그램 안 하나의 작은 프로그램
	
	private Player player; //jlayer 라이브러리
	private boolean isLoop; //현재 곡이 무한반복인지, 한번 재생 후 꺼지는 지 설정
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	
	public Music(String name, boolean isLoop) { //생성자
		try {
			this.isLoop=isLoop; //isLoop 변수 초기화
			file=new File(Main.class.getResource("/music/"+name).toURI()); //mp3파일위치 가져오기
			fis=new FileInputStream(file); 
			bis=new BufferedInputStream(fis); //파일을 버퍼에 담아 읽기
			player=new Player(bis);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public int getTime() { //현재 음악 실행 위치를 알려줌, 음악에 맞춰 노트 떨어뜨리는 속도 맞출 때 getTime 실행
		if(player==null) {
			return 0;
		}
		return player.getPosition();
	}
	public void close() { //뒤로가기 누를 때 곡이 안정적으로 종료
		isLoop=false;
		player.close();
		this.interrupt(); //해당 thread를 중지 상태로 만듦
	}
	@Override 
	public void run() { //thread를 상속받으면 무조건 사용해야 하는 함수
		try {
			do {
				player.play(); //곡 실행
				fis=new FileInputStream(file); 
				bis=new BufferedInputStream(fis); //파일을 버퍼에 담아 읽기
				player=new Player(bis);
			}while(isLoop); //isLoop가 true이면 해당 곡을 무한반복
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
