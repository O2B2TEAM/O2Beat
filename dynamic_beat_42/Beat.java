package dynamic_beat_42;

public class Beat {
	
	private int time; //타이밍
	private String noteName; //노트종류
	
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public String getNoteName() {
		return noteName;
	}
	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}
	//생성자
	public Beat(int time, String noteName) {
		super();
		this.time = time;
		this.noteName = noteName;
	}

	
	
}
