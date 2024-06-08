package dynamic_beat_24;

public class Main {
	
	public static final int SCREEN_WIDTH=1280; //public static 
	public static final int SCREEN_HEIGHT=720; //모든 프로그램 정적
	public static final int NOTE_SPEED=3; //노트 떨어지는 속도
	public static final int SLEEP_TIME=10; //노트 떨어지는 주기(0.01초)
	public static final int REACH_TIME=2; //노트 생성 후 판정 바 도달까지의 시간

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DynamicBeat(); //객체 생성
	}

}
