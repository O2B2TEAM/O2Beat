package dynamic_beat_16;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import dynamic_beat_16.Main;

public class Game extends Thread {
	// 게임정보 인스턴스
		private Image gameInfoImage = new ImageIcon(Main.class.getResource("/images/gameInfo.png")).getImage();	
		// 판정라인 인스턴스
		private Image judgementLineImage = new ImageIcon(Main.class.getResource("/images/judgementLine.png")).getImage();
		// 노트루트선 인스턴스
		private Image noteRouteLineImage = new ImageIcon(Main.class.getResource("/images/noteRouteLine.png")).getImage();
		// 노트루트 인스턴스
		private Image noteRouteSImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		private Image noteRouteDImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		private Image noteRouteFImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		private Image noteRouteSpace1Image = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		private Image noteRouteSpace2Image = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		private Image noteRouteJImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		private Image noteRouteKImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		private Image noteRouteLImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		//판정화염이미지
		private Image flareImage;
		//판정글자이미지
		private Image judgeImage;
		// 키패드 색상변경 인스턴스
		private Image keyPadSImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
		private Image keyPadDImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
		private Image keyPadFImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
		private Image keyPadSpace1Image = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
		private Image keyPadSpace2Image = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
		private Image keyPadJImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
		private Image keyPadKImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
		private Image keyPadLImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
		
		//게임에 맞게 곡 재생하기
		private String titleName; //실행할 곡 이름
		private String difficulty; //난이도
		private String musicTitle;
		private Music gameMusic;//곡
		
		//각각의 노트를 생성되는 순간마다 관리해주는 배열
		ArrayList<Note> noteList=new ArrayList<Note>();
		
		//생성자
		public Game(String titleName,String difficulty,String musicTitle) {
			this.titleName=titleName;
			this.difficulty=difficulty;
			this.musicTitle=musicTitle;
			gameMusic=new Music(this.musicTitle,false);
		}
		
		
	public void screenDraw(Graphics2D g) {
		//draw이미지 함수는 나중에 올 수록 화면상에 위에 위치한다.
		// 노트루트 이미지
		g.drawImage(noteRouteSImage, 228, 30, null);
		g.drawImage(noteRouteDImage, 332, 30, null);
		g.drawImage(noteRouteFImage, 436, 30, null);
		g.drawImage(noteRouteSpace1Image, 540, 30, null);
		g.drawImage(noteRouteSpace2Image, 640, 30, null);
		g.drawImage(noteRouteJImage, 744, 30, null);
		g.drawImage(noteRouteKImage, 848, 30, null);
		g.drawImage(noteRouteLImage, 952, 30, null);
		// 노트루트라인 이미지
		g.drawImage(noteRouteLineImage, 224, 30, null);
		g.drawImage(noteRouteLineImage, 328, 30, null);
		g.drawImage(noteRouteLineImage, 432, 30, null);
		g.drawImage(noteRouteLineImage, 536, 30, null);
		g.drawImage(noteRouteLineImage, 740, 30, null);
		g.drawImage(noteRouteLineImage, 844, 30, null);
		g.drawImage(noteRouteLineImage, 948, 30, null);
		g.drawImage(noteRouteLineImage, 1052, 30, null);
		// gameInfo 이미지
		g.drawImage(gameInfoImage, 0, 660, null);
		//노트 생성
		for(int i=0;i<noteList.size();i++) {
			Note note=noteList.get(i);
			if(note.getY()>620) {
				judgeImage=new ImageIcon(Main.class.getResource("/images/judgeMiss.png")).getImage();
			}
			//사용되지 않는 노트 화면에서 지우기
			if(!note.isProceeded()) {
				noteList.remove(i);
				i--;
			}else {
				note.screenDraw(g);	
			}
		}
		//글자 출력
		g.setColor(Color.white);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);//글자깨짐방지
		//곡정보 출력(점수,난이도)
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString(titleName,20,702);
		g.drawString(difficulty,1190,702);
		// 키패드 이미지
		g.drawImage(keyPadSImage, 228, 580, null);
		g.drawImage(keyPadDImage, 332, 580, null);
		g.drawImage(keyPadFImage, 436, 580, null);
		g.drawImage(keyPadSpace1Image, 540, 580, null);
		g.drawImage(keyPadSpace2Image, 640, 580, null);
		g.drawImage(keyPadJImage, 744, 580, null);
		g.drawImage(keyPadKImage, 848, 580, null);
		g.drawImage(keyPadLImage, 952, 580, null);
		// 노트판정 이미지
		g.drawImage(judgementLineImage, 0, 580, null);
		//키패드 출력
		g.setFont(new Font("Arial",Font.PLAIN,26));
		g.setColor(Color.DARK_GRAY);
		g.drawString("S",270,609);
		g.drawString("D",374,609);
		g.drawString("F",478,609);
		g.drawString("Space Bar",580,609);
		g.drawString("J",784,609);
		g.drawString("K",889,609);
		g.drawString("L",993,609);
		//곡정보 출력(점수)
		g.setColor(Color.LIGHT_GRAY);
		g.setFont(new Font("Elephant",Font.BOLD,30));
		g.drawString("000000",565,702);
		//판정화염이미지
		g.drawImage(flareImage,150,0,null); 
		//판정글자이미지
		g.drawImage(judgeImage,470,300,null); 
	}
	//S를 눌렀을 때
	public void pressS() {
		judge("S");
		noteRouteSImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadSImage = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum6.mp3",false).start(); //반복재생 false
	}
	//S를 뗐을 때
	public void releaseS() {
		noteRouteSImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadSImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	}	
	public void pressD() {
		judge("D");
		noteRouteDImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadDImage = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum6.mp3",false).start(); //반복재생 false
	}
	public void releaseD() {
		noteRouteDImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadDImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	}
	public void pressF() {
		judge("F");
		noteRouteFImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadFImage = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum6.mp3",false).start(); //반복재생 false
	}
	public void releaseF() {
		noteRouteFImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadFImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	}
	public void pressSpace() {
		judge("Space");
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadSpace1Image = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadSpace2Image = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum1.mp3",false).start(); //반복재생 false
	}
	public void releaseSpace() {
		noteRouteSpace1Image = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		noteRouteSpace2Image = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadSpace1Image = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
		keyPadSpace2Image = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	} 
	public void pressJ() {
		judge("J");
		noteRouteJImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadJImage = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum6.mp3",false).start(); //반복재생 false
	}
	public void releaseJ() {
		noteRouteJImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadJImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	}
	public void pressK() {
		judge("K");
		noteRouteKImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadKImage = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum6.mp3",false).start(); //반복재생 false
	}
	public void releaseK() { 
		noteRouteKImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadKImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	}
	public void pressL() {
		judge("L");
		noteRouteLImage = new ImageIcon(Main.class.getResource("/images/noteRoutePressed.png")).getImage();
		keyPadLImage = new ImageIcon(Main.class.getResource("/images/keyPadPressed.png")).getImage();
		new Music("drum6.mp3",false).start(); //반복재생 false
	}
	public void releaseL() {
		noteRouteLImage = new ImageIcon(Main.class.getResource("/images/noteRoute.png")).getImage();
		keyPadLImage = new ImageIcon(Main.class.getResource("/images/keyPadBasic.png")).getImage();
	}
	
	@Override
	public void run() {
		dropNotes(this.titleName);
	}
	//종료 메소드
	public void close() {
		gameMusic.close();
		this.interrupt();
	}
	//각각의 노트들이 떨어질 수 있도록 하는 메소드
	public void dropNotes(String titleName) {
		Beat[] beats= null;
		if(titleName.equals("music1_piSong")&&difficulty.equals("Easy")) {
			int startTime=4460-Main.REACH_TIME*1000;
			int gap=125;
			beats=new Beat[] {
					new Beat(startTime+gap*2,"D"),
					new Beat(startTime+gap*4,"S"),
					new Beat(startTime+gap*6,"D"),
					new Beat(startTime+gap*8,"S"),
					new Beat(startTime+gap*10,"D"),
					new Beat(startTime+gap*12,"J"),
					new Beat(startTime+gap*14,"K"),
					new Beat(startTime+gap*16,"L"),
					new Beat(startTime+gap*18,"J"),
					new Beat(startTime+gap*20,"K"),
					new Beat(startTime+gap*22,"J"),
					new Beat(startTime+gap*24,"K"),
					new Beat(startTime+gap*26,"J"),
					new Beat(startTime+gap*28,"K"),
					new Beat(startTime+gap*30,"J"),
					new Beat(startTime+gap*32,"K"),
					new Beat(startTime+gap*36,"S"),
					new Beat(startTime+gap*38,"D"),
					new Beat(startTime+gap*40,"S"),
					new Beat(startTime+gap*42,"D"),
					new Beat(startTime+gap*44,"S"),
					new Beat(startTime+gap*46,"D"),
					new Beat(startTime+gap*48,"K"),
					new Beat(startTime+gap*49,"K"),
					new Beat(startTime+gap*50,"L"),
					new Beat(startTime+gap*52,"F"),
					new Beat(startTime+gap*52,"Space"),
					new Beat(startTime+gap*52,"J")
			};
		}
		else if(titleName.equals("music1_piSong")&&difficulty.equals("Hard")) {
			int startTime=1000-Main.REACH_TIME*1000;
			beats=new Beat[] {
					new Beat(startTime,"Space"),
			};
		}
		else if(titleName.equals("music2")&&difficulty.equals("Easy")) {
			int startTime=1000-Main.REACH_TIME*1000;
			beats=new Beat[] {
					new Beat(startTime,"Space"),
			};
		}
		else if(titleName.equals("music2")&&difficulty.equals("Hard")) {
			int startTime=1000-Main.REACH_TIME*1000;
			beats=new Beat[] {
					new Beat(startTime,"Space"),
			};
		}
		else if(titleName.equals("music3")&&difficulty.equals("Easy")) {
			int startTime=1000-Main.REACH_TIME*1000;
			beats=new Beat[] {
					new Beat(startTime,"Space"),
			};
		}
		else if(titleName.equals("music3")&&difficulty.equals("Hard")) {
			int startTime=1000-Main.REACH_TIME*1000;
			beats=new Beat[] {
					new Beat(startTime,"Space"),
			};
		}
		else if(titleName.equals("music4")&&difficulty.equals("Easy")) {
			int startTime=1000-Main.REACH_TIME*1000;
			beats=new Beat[] {
					new Beat(startTime,"Space"),
			};
		}
		else if(titleName.equals("music4")&&difficulty.equals("Hard")) {
			int startTime=1000-Main.REACH_TIME*1000;
			beats=new Beat[] {
					new Beat(startTime,"Space"),
			};
		}
		int i=0;
		gameMusic.start(); //여기에 두어야 배열이 초기화 되는 시간에서 오는 시간을 줄일 수 있다.
		while(i<beats.length&&!isInterrupted()) {
			boolean dropped=false;
			if(beats[i].getTime()<=gameMusic.getTime()) {//비트가 떨어지는 시간이 음악에서 얻는 시간보다 작다면
				Note note=new Note(beats[i].getNoteName()); //현재의 비트에서 얻어오기
				note.start(); //노트 떨어지기
				noteList.add(note); //JFrame에 노트 추가 떨어뜨리기
				i++; //각각의 노트 하나씩 전부 적ㅇ
				dropped=true;
			}if(!dropped) {
				try {
					Thread.sleep(5);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
		//판정함수
		public void judge(String input) {
			//먼저 내려온 노트가 먼저 판정될 수 있도록 큐처럼 구현한 함수
			//i가 반복하면서 방문
			for(int i=0; i<noteList.size();i++) { //인덱스를 앞에서부터 탐색하기때문에 가장 먼저 생성된 노트를 찾음
				Note note=noteList.get(i); //하나의 노트씩 판정 수행
			if(input.equals(note.getNoteType())) { //입력한 노트와 해당 노트가 같다면 판정문 수행하고 break 없으면 무시
				judgeEvent(note.judge()); //판정문 수행
				break;
			}
		}
	}
		public void judgeEvent(String judge) {
			if(!judge.equals("None")) {
				flareImage=new ImageIcon(Main.class.getResource("/images/flare.png")).getImage();
			}
			if(judge.equals("Miss")) {
				judgeImage=new ImageIcon(Main.class.getResource("/images/judgeMiss.png")).getImage();
			}else if(judge.equals("Late")) {
				judgeImage=new ImageIcon(Main.class.getResource("/images/judgeLate.png")).getImage();
			}else if(judge.equals("Good")) {
				judgeImage=new ImageIcon(Main.class.getResource("/images/judgeGood.png")).getImage();
			}else if(judge.equals("Great")) {
				judgeImage=new ImageIcon(Main.class.getResource("/images/judgeGreat.png")).getImage();
			}else if(judge.equals("Early")) {
				judgeImage=new ImageIcon(Main.class.getResource("/images/judgeEarly.png")).getImage();
			}else if(judge.equals("Perfect")) {
				judgeImage=new ImageIcon(Main.class.getResource("/images/judgePerfect.png")).getImage();
			}
		}
		
		
		
		
		
}