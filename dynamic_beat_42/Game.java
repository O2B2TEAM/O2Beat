package dynamic_beat_42;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import dynamic_beat_42.Main;

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
		//소녀 이미지
		private Image girlImage;
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
		
		// 산소바 관련 변수
		private int oxygen; // 산소 초기값
		private final int OXYGEN_DECREASE = 5; // 산소 감소량
		private final int OXYGEN_INCREASE = 5;  // 산소 증가량
		
		int score=0;
		int highScore=0;
		
		//각각의 노트를 생성되는 순간마다 관리해주는 배열
		ArrayList<Note> noteList=new ArrayList<Note>();
		private boolean isGameOver = false; // 게임 오버 상태 변수
		
		// 게임 오버 이미지 인스턴스
	    private Image gameOverImage = new ImageIcon(Main.class.getResource("/images/gameover.png")).getImage();
		
		//생성자
		public Game(String titleName,String difficulty,String musicTitle, String username) {
			this.titleName=titleName;
			this.difficulty=difficulty;
			this.musicTitle=musicTitle;
			this.highScore = Database.getHighScore(username); // DB에서 사용자 최고 점수 가져오기
			gameMusic=new Music(this.musicTitle,false);
			
			// 난이도에 따른 초기 산소값 설정
			if (difficulty.equals("Easy")) {
				this.oxygen = 80; // Easy 모드일 때 초기 산소값 80%
			} else if (difficulty.equals("Hard")) {
				this.oxygen = 50; // Hard 모드일 때 초기 산소값 50%
			}
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
				girlImage=new ImageIcon(Main.class.getResource("/images/girlMiss.png")).getImage();
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
		
		if(highScore<score) {
			highScore=score;
		}
		
        // 게임 점수 출력
        if(score<0){ // 점수가 0 미만으로 내려가면 그냥 0 출력
            g.drawString(String.valueOf(0), 620, 702);
        }else{ // 아니면 그대로 출력
            g.drawString(String.valueOf(score), 620, 702);
        }
        g.drawString(String.valueOf(highScore), 750, 702);
		
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
		//판정화염이미지
		g.drawImage(flareImage,150,0,null); 
		g.drawImage(girlImage,18,130,null);
		//판정글자이미지
		g.drawImage(judgeImage,470,300,null);
		
		// 산소바 그리기 (아래에서 위로 증가하는 형태)
		g.setColor(Color.GREEN);
		g.fillRect(1120, 560 - (int)(3.6 * oxygen), 100, (int)(3.6 * oxygen)); // 산소바의 위치와 크기 조정
		g.setColor(Color.WHITE);
		g.drawRect(1120, 200, 100, 360); // 산소바 테두리
	    
	    // 산소 수치 표시
	    g.setFont(new Font("Arial", Font.BOLD, 20));
	    g.drawString("Oxygen: " + oxygen + "%", 1100, 190);
	    
	    // 게임 오버 상태라면 게임 오버 이미지 출력
        if (isGameOver) {
            g.drawImage(gameOverImage, 0, 0, null); // 화면 전체에 게임 오버 이미지 그리기
        }

		
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
	    // 새로운 스레드를 추가하여 산소 감소를 처리합니다.
	    Thread oxygenDecreaseThread = new Thread(() -> {
	        while (!isInterrupted()) {
	            try {
	                Thread.sleep(1000); // 1초마다 실행
	            } catch (InterruptedException e) {
	                return;
	            }
	            oxygen -= OXYGEN_DECREASE;
	            if (oxygen < 0) {
	                oxygen = 0;
	            }
	            if (oxygen == 0) {
	            	isGameOver = true; // 게임 오버 상태 설정
	                close();
	                break;
	            }
	        }
	    });

	    oxygenDecreaseThread.start();
	    dropNotes(this.titleName);
	}

	public void close() {
	    gameMusic.close();
	    this.interrupt();
	    isGameOver = true;
	}
	//각각의 노트들이 떨어질 수 있도록 하는 메소드
	public void dropNotes(String titleName) {
		Beat[] beats= null;
		if(titleName.equals("PI Song")&&difficulty.equals("Easy")) {
			//int startTime=4460-Main.REACH_TIME*1000;
			int startTime=1920-Main.REACH_TIME*1000; //첫 비트 떨어지는 시간 약 1.92초
	         int gap=333; // 3/4박자, 4/4박자: 250 , 1/8 박자일 경우 125)
	         beats=new Beat[] {
	               new Beat(startTime+gap*2,"Space"),     // (3141)5
	               
	               new Beat(startTime+gap*8,"K"),     // 2
	               new Beat(startTime+gap*10,"J"),    //s 5(1)
	               
	               new Beat(startTime+gap*17,"F"),    // 3
	               new Beat(startTime+gap*18,"J"),    // 2
	               new Beat(startTime+gap*19,"S"),    // 1
	               new Beat(startTime+gap*20,"D"),    // 2
	               new Beat(startTime+gap*21,"L"),    // 5
	               
	               new Beat(startTime+gap*28,"F"),    // 4
	               new Beat(startTime+gap*31,"S"),    // 5(3)
	               
	               new Beat(startTime+gap*47,"F"),    // 3
	               new Beat(startTime+gap*48,"F"),    // 1
	               new Beat(startTime+gap*49,"S"),    // 4
	               new Beat(startTime+gap*50,"F"),    // 1
	               new Beat(startTime+gap*52,"D"),    // 5
	               
	               new Beat(startTime+gap*56,"L"),    // 2
	               new Beat(startTime+gap*58,"S"),    // 5
	               new Beat(startTime+gap*61,"S"),    // 1
	               
	               new Beat(startTime+gap*65,"Space"),// 3
	               new Beat(startTime+gap*66,"S"),    // 2
	               new Beat(startTime+gap*67,"D"),    // 1
	               new Beat(startTime+gap*68,"F"),    // 2
	               new Beat(startTime+gap*69,"D"),    // 5
	               
	               new Beat(startTime+gap*74,"S"),  // 4
	               new Beat(startTime+gap*75,"L"),  // 5
	               new Beat(startTime+gap*77,"S"),  // 3
	               
	               new Beat(startTime+gap*84,"F"),    // 3
	               new Beat(startTime+gap*85,"J"),    // 2
	               new Beat(startTime+gap*86,"S"),    // 1
	               new Beat(startTime+gap*87,"D"),    // 2
	               new Beat(startTime+gap*88,"L"),    // 5
	               
	               new Beat(startTime+gap*92,"D"),    // 2
	               new Beat(startTime+gap*94,"F"),    // 5
	               new Beat(startTime+gap*96,"K"),    // 1
	               
	               new Beat(startTime+gap*103,"F"),    // 3
	               new Beat(startTime+gap*104,"F"),    // 1
	               new Beat(startTime+gap*105,"S"),    // 4
	               new Beat(startTime+gap*106,"F"),    // 1
	               new Beat(startTime+gap*107,"D"),    // 5
	               
	               new Beat(startTime+gap*111,"L"),    // 2
	               new Beat(startTime+gap*112,"S"),    // 5
	               new Beat(startTime+gap*114,"S"),    // 1
	               
	               new Beat(startTime+gap*120,"Space"),// 3
	               new Beat(startTime+gap*121,"S"),    // 2
	               new Beat(startTime+gap*122,"D"),    // 1
	               new Beat(startTime+gap*123,"F"),    // 2
	               new Beat(startTime+gap*124,"D"),    // 5

	               new Beat(startTime+gap*128,"S"),    // 4
	               new Beat(startTime+gap*132,"L"),    // 5
	               new Beat(startTime+gap*134,"S"),    // 3

	               new Beat(startTime+gap*136,"F"),    // 3
	               new Beat(startTime+gap*137,"F"),    // 1
	               new Beat(startTime+gap*138,"S"),    // 4
	               new Beat(startTime+gap*139,"F"),    // 1
	               new Beat(startTime+gap*140,"D"),    // 5

	               new Beat(startTime+gap*145,"L"),    // 2
	               new Beat(startTime+gap*146,"S"),    // 5
	               new Beat(startTime+gap*148,"S"),    // 1
	                   
	               // 여기서부터 변주 시작됨
	               new Beat(startTime+gap*150,"F"),    // 5
	               new Beat(startTime+gap*151,"F"),    // 1
	               new Beat(startTime+gap*152,"S"),    // 2
	               new Beat(startTime+gap*153,"F"),    // 1
	               new Beat(startTime+gap*154,"D"),    // 5
	               new Beat(startTime+gap*156,"D"),    // 1
	               new Beat(startTime+gap*157,"D"),    // 3

	               new Beat(startTime+gap*159,"L"),    // 1
	               new Beat(startTime+gap*160,"S"),    // 5
	               new Beat(startTime+gap*162,"S"),    // 3
	               new Beat(startTime+gap*164,"F"),    // 2
	               new Beat(startTime+gap*165,"J"),    // 1
	               new Beat(startTime+gap*166,"S"),    // 5
	               new Beat(startTime+gap*167,"D"),    // 1
	               
	               new Beat(startTime+gap*168,"L"),    // 5
	               new Beat(startTime+gap*173,"F"),    // 1
	               new Beat(startTime+gap*175,"S"),    // 2
	               new Beat(startTime+gap*177,"F"),    // 1
	               new Beat(startTime+gap*178,"F"),    // 5
	               new Beat(startTime+gap*179,"S"),    // 1
	               new Beat(startTime+gap*180,"F"),    // 3
	               
	               new Beat(startTime+gap*181,"D"),    // 1
	               new Beat(startTime+gap*185,"L"),    // 2
	               new Beat(startTime+gap*186,"S"),    // 3
	               new Beat(startTime+gap*188,"S"),    // 5
	               
	               //변주
	               new Beat(startTime+gap*189,"F"),    // 3
	               new Beat(startTime+gap*190,"F"),    // 1
	               new Beat(startTime+gap*191,"S"),    // 4
	               new Beat(startTime+gap*192,"F"),    // 1
	               
	               new Beat(startTime+gap*194,"D"),    // 5
	               new Beat(startTime+gap*196,"L"),    // 2
	               new Beat(startTime+gap*197,"S"),    // 5
	               new Beat(startTime+gap*199,"S"),    // 1
	               
	               new Beat(startTime+gap*203,"F"),    // 3
	               new Beat(startTime+gap*204,"J"),    // 2
	               new Beat(startTime+gap*205,"S"),    // 1
	               new Beat(startTime+gap*206,"D"),    // 2
	               
	               new Beat(startTime+gap*207,"L"),    // 5
	               new Beat(startTime+gap*211,"F"),    // 4
	               new Beat(startTime+gap*213,"S"),    // 5(3)
	               new Beat(startTime+gap*215,"F"),    // 3
	               
	               new Beat(startTime+gap*216,"F"),    // 1
	               new Beat(startTime+gap*217,"S"),
	               new Beat(startTime+gap*218,"L"),
	               new Beat(startTime+gap*219,"F"),
	               
	               
	               new Beat(startTime+gap*223,"L"),    // 2
	               new Beat(startTime+gap*224,"S"),    // 5
	               new Beat(startTime+gap*226,"S"),    // 1
	               
	               new Beat(startTime+gap*228,"F"),    // 3
	               new Beat(startTime+gap*229,"J"),    // 2
	               new Beat(startTime+gap*230,"S"),    // 1
	               new Beat(startTime+gap*231,"D"),    // 2
	               
	               new Beat(startTime+gap*233,"L"),    // 5
	               new Beat(startTime+gap*235,"F"),    // 4
	               new Beat(startTime+gap*236,"S"),    // 5(3)
	               new Beat(startTime+gap*238,"F"),    // 3
	               
	               new Beat(startTime+gap*239,"F"),    // 1
	               new Beat(startTime+gap*240,"S"),
	               new Beat(startTime+gap*241,"L"),
	               new Beat(startTime+gap*242,"F"),
	               new Beat(startTime+gap*243,"J"),
	               
	               new Beat(startTime+gap*246,"L"),    // 2
	               new Beat(startTime+gap*247,"S"),    // 5
	               new Beat(startTime+gap*248,"S"),    // 1
	               
	               new Beat(startTime+gap*250,"F"),    // 3
	               new Beat(startTime+gap*251,"J"),    // 2
	               new Beat(startTime+gap*252,"S"),    // 1
	               new Beat(startTime+gap*253,"D"),    // 2
	               
	               new Beat(startTime+gap*255,"D"),    // 5
	               new Beat(startTime+gap*256,"K"),    // 5(3)
	               new Beat(startTime+gap*257,"D"),    // 5
	               new Beat(startTime+gap*258,"K"),    // 5(3)
	               
	               new Beat(startTime+gap*260,"S"),    // 5
	               new Beat(startTime+gap*261,"F"),    // 4
	               new Beat(startTime+gap*262,"J"),    // 5(3)
	               new Beat(startTime+gap*263,"L"),    // 3
	               
	               new Beat(startTime+gap*267,"F"),    // 3
	               new Beat(startTime+gap*268,"F"),    // 1
	               new Beat(startTime+gap*269,"S"),    // 4
	               new Beat(startTime+gap*270,"F"),    // 1
	               new Beat(startTime+gap*272,"D"),    // 5
	               
	               new Beat(startTime+gap*276,"L"),    // 2
	               new Beat(startTime+gap*278,"S"),    // 5
	               new Beat(startTime+gap*281,"S"),    // 1
	               
	               new Beat(startTime+gap*285,"Space"),// 3
	               new Beat(startTime+gap*289,"S"),    // 2
	               new Beat(startTime+gap*290,"D"),    // 1
	               new Beat(startTime+gap*291,"F"),    // 2
	               new Beat(startTime+gap*292,"D"),    // 5
	               
	               new Beat(startTime+gap*298,"S"),  // 4
	               new Beat(startTime+gap*299,"L"),  // 5
	               new Beat(startTime+gap*300,"S"),  // 3
	               
	               new Beat(startTime+gap*306,"F"),    // 3
	               new Beat(startTime+gap*307,"F"),    // 1
	               new Beat(startTime+gap*308,"S"),    // 4
	               new Beat(startTime+gap*309,"F"),    // 1
	               new Beat(startTime+gap*310,"D"),    // 5
	               
	               new Beat(startTime+gap*314,"L"),    // 2
	               new Beat(startTime+gap*316,"S"),    // 5
	               new Beat(startTime+gap*319,"S"),    // 1
	               
	               new Beat(startTime+gap*323,"J"),// 3
	               new Beat(startTime+gap*324,"S"),    // 2
	               new Beat(startTime+gap*325,"D"),    // 1
	               new Beat(startTime+gap*326,"F"),    // 2
	               new Beat(startTime+gap*327,"D"),    // 5
	               
	               new Beat(startTime+gap*332,"S"),  // 4
	               new Beat(startTime+gap*335,"L"),  // 5
	               new Beat(startTime+gap*338,"S"),  // 3
	               
	               new Beat(startTime+gap*350,"Space"),  // 3
        
	         };
	      }
		else if(titleName.equals("PI Song")&&difficulty.equals("Hard")) {
			int startTime=1000-Main.REACH_TIME*1000;
			int gap=333; 
			beats=new Beat[] {
					new Beat(startTime+gap*2,"Space"),     // (3141)5
		               
					new Beat(startTime+gap*8,"K"),     // 2
					new Beat(startTime+gap*8,"J"),    // 추가
					new Beat(startTime+gap*10,"J"),    //s 5(1)
					new Beat(startTime+gap*10,"D"),    // 추가
							               
					new Beat(startTime+gap*17,"F"),    // 3
					new Beat(startTime+gap*17,"S"),    // 추가
					new Beat(startTime+gap*18,"J"),    // 2
					new Beat(startTime+gap*18,"K"),    // 추가
					new Beat(startTime+gap*19,"S"),    // 1
					new Beat(startTime+gap*19,"F"),    // 추가
					new Beat(startTime+gap*20,"D"),    // 2
					new Beat(startTime+gap*20,"S"),    // 추가
					new Beat(startTime+gap*21,"L"),    // 5
					new Beat(startTime+gap*21,"F"),    // 추가
							               
					new Beat(startTime+gap*28,"F"),    // 4
					new Beat(startTime+gap*28,"D"),    // 추가
					new Beat(startTime+gap*31,"S"),    // 5(3)
					new Beat(startTime+gap*31,"F"),    // 추가
							               
					new Beat(startTime+gap*47,"F"),    // 3
					new Beat(startTime+gap*47,"J"),    // 추가
					new Beat(startTime+gap*48,"F"),    // 1
					new Beat(startTime+gap*48,"K"),    // 추가
					new Beat(startTime+gap*49,"S"),    // 4
					new Beat(startTime+gap*49,"D"),    // 추가
					new Beat(startTime+gap*50,"F"),    // 1
					new Beat(startTime+gap*50,"L"),    // 추가
					new Beat(startTime+gap*52,"D"),    // 5
					new Beat(startTime+gap*52,"F"),    // 추가
							               
					new Beat(startTime+gap*56,"L"),    // 2
					new Beat(startTime+gap*56,"K"),    // 추가
					new Beat(startTime+gap*58,"S"),    // 5
					new Beat(startTime+gap*58,"J"),    // 추가
					new Beat(startTime+gap*61,"S"),    // 1
					new Beat(startTime+gap*61,"F"),    // 추가
							               
					new Beat(startTime+gap*65,"Space"),// 3
					new Beat(startTime+gap*65,"D"),    // 추가
					new Beat(startTime+gap*66,"S"),    // 2
					new Beat(startTime+gap*66,"F"),    // 추가
					new Beat(startTime+gap*67,"D"),    // 1
					new Beat(startTime+gap*67,"J"),    // 추가
					new Beat(startTime+gap*68,"F"),    // 2
					new Beat(startTime+gap*68,"K"),    // 추가
					new Beat(startTime+gap*69,"D"),    // 5
					new Beat(startTime+gap*69,"L"),    // 추가
							               
					new Beat(startTime+gap*74,"S"),  // 4
					new Beat(startTime+gap*74,"F"),  // 추가
					new Beat(startTime+gap*75,"L"),  // 5
					new Beat(startTime+gap*75,"K"),  // 추가
					new Beat(startTime+gap*77,"S"),  // 3
					new Beat(startTime+gap*77,"J"),  // 추가
							               
					new Beat(startTime+gap*84,"F"),    // 3
					new Beat(startTime+gap*84,"D"),    // 추가
					new Beat(startTime+gap*85,"J"),    // 2
					new Beat(startTime+gap*85,"S"),    // 추가
					new Beat(startTime+gap*86,"S"),    // 1
					new Beat(startTime+gap*86,"F"),    // 추가
					new Beat(startTime+gap*87,"D"),    // 2
					new Beat(startTime+gap*87,"K"),    // 추가
					new Beat(startTime+gap*88,"L"),    // 5
					new Beat(startTime+gap*88,"F"),    // 추가
							               
					new Beat(startTime+gap*92,"D"),    // 2
					new Beat(startTime+gap*92,"S"),    // 추가
					new Beat(startTime+gap*94,"F"),    // 5
					new Beat(startTime+gap*94,"J"),    // 추가
					new Beat(startTime+gap*96,"K"),    // 1
					new Beat(startTime+gap*96,"L"),    // 추가
							               
					new Beat(startTime+gap*103,"F"),    // 3
					new Beat(startTime+gap*103,"D"),    // 추가
					new Beat(startTime+gap*104,"F"),    // 1
					new Beat(startTime+gap*104,"S"),    // 추가
					new Beat(startTime+gap*105,"S"),    // 4
					new Beat(startTime+gap*105,"F"),    // 추가
					new Beat(startTime+gap*106,"F"),    // 1
					new Beat(startTime+gap*106,"J"),    // 추가
					new Beat(startTime+gap*107,"D"),    // 5
					new Beat(startTime+gap*107,"K"),    // 추가
							               
					new Beat(startTime+gap*111,"L"),    // 2
					new Beat(startTime+gap*111,"S"),    // 추가
					new Beat(startTime+gap*112,"S"),    // 5
					new Beat(startTime+gap*112,"F"),    // 추가
					new Beat(startTime+gap*114,"S"),    // 1
					new Beat(startTime+gap*114,"F"),    // 추가
							               
					new Beat(startTime+gap*120,"Space"),// 3
					new Beat(startTime+gap*120,"D"),    // 추가
					new Beat(startTime+gap*121,"S"),    // 2
					new Beat(startTime+gap*121,"J"),    // 추가
					new Beat(startTime+gap*122,"D"),    // 1
					new Beat(startTime+gap*122,"K"),    // 추가
					new Beat(startTime+gap*123,"F"),    // 2
					new Beat(startTime+gap*123,"L"),    // 추가
					new Beat(startTime+gap*124,"D"),    // 5
					new Beat(startTime+gap*124,"S"),    // 추가

					new Beat(startTime+gap*128,"S"),    // 4
					new Beat(startTime+gap*128,"F"),    // 추가
					new Beat(startTime+gap*132,"L"),    // 5
					new Beat(startTime+gap*132,"K"),    // 추가
					new Beat(startTime+gap*134,"S"),    // 3
					new Beat(startTime+gap*134,"J"),    // 추가

					new Beat(startTime+gap*136,"F"),    // 3
					new Beat(startTime+gap*136,"D"),    // 추가
					new Beat(startTime+gap*137,"F"),    // 1
					new Beat(startTime+gap*137,"S"),    // 추가
					new Beat(startTime+gap*138,"S"),    // 4
					new Beat(startTime+gap*138,"F"),    // 추가
					new Beat(startTime+gap*139,"F"),    // 1
					new Beat(startTime+gap*139,"J"),    // 추가
					new Beat(startTime+gap*140,"D"),    // 5
					new Beat(startTime+gap*140,"K"),    // 추가

					new Beat(startTime+gap*145,"L"),    // 2
					new Beat(startTime+gap*145,"S"),    // 추가
					new Beat(startTime+gap*146,"S"),    // 5
					new Beat(startTime+gap*146,"F"),    // 추가
					new Beat(startTime+gap*148,"S"),    // 1
					new Beat(startTime+gap*148,"F"),    // 추가
								                   
					// 여기서부터 변주 시작됨
					new Beat(startTime+gap*150,"F"),    // 5
					new Beat(startTime+gap*150,"D"),    // 추가
					new Beat(startTime+gap*151,"F"),    // 1
					new Beat(startTime+gap*151,"S"),    // 추가
					new Beat(startTime+gap*152,"S"),    // 2
					new Beat(startTime+gap*152,"F"),    // 추가
					new Beat(startTime+gap*153,"F"),    // 1
					new Beat(startTime+gap*153,"J"),    // 추가
					new Beat(startTime+gap*154,"D"),    // 5
					new Beat(startTime+gap*154,"K"),    // 추가
					new Beat(startTime+gap*156,"D"),    // 1
					new Beat(startTime+gap*156,"L"),    //
					new Beat(startTime+gap*156,"L"),    // 추가
					new Beat(startTime+gap*157,"D"),    // 3
					new Beat(startTime+gap*157,"S"),    // 추가

					new Beat(startTime+gap*159,"L"),    // 1
					new Beat(startTime+gap*159,"K"),    // 추가
					new Beat(startTime+gap*160,"S"),    // 5
					new Beat(startTime+gap*160,"J"),    // 추가
					new Beat(startTime+gap*162,"S"),    // 3
					new Beat(startTime+gap*162,"F"),    // 추가
					new Beat(startTime+gap*164,"F"),    // 2
					new Beat(startTime+gap*164,"F"),    // 추가
					new Beat(startTime+gap*165,"J"),    // 1
					new Beat(startTime+gap*165,"D"),    // 추가
					new Beat(startTime+gap*166,"S"),    // 5
					new Beat(startTime+gap*166,"F"),    // 추가
					new Beat(startTime+gap*167,"D"),    // 1
					new Beat(startTime+gap*167,"K"),    // 추가
							               
					new Beat(startTime+gap*168,"L"),    // 5
					new Beat(startTime+gap*168,"F"),    // 추가
					new Beat(startTime+gap*173,"F"),    // 1
					new Beat(startTime+gap*173,"D"),    // 추가
					new Beat(startTime+gap*175,"S"),    // 2
					new Beat(startTime+gap*175,"F"),    // 추가
					new Beat(startTime+gap*177,"F"),    // 1
					new Beat(startTime+gap*177,"J"),    // 추가
					new Beat(startTime+gap*178,"F"),    // 5
					new Beat(startTime+gap*178,"K"),    // 추가
					new Beat(startTime+gap*179,"S"),    // 1
					new Beat(startTime+gap*179,"L"),    // 추가
					new Beat(startTime+gap*180,"F"),    // 3
					new Beat(startTime+gap*180,"D"),    // 추가
							               
					new Beat(startTime+gap*181,"D"),    // 1
					new Beat(startTime+gap*181,"S"),    // 추가
					new Beat(startTime+gap*185,"L"),    // 2
					new Beat(startTime+gap*185,"K"),    // 추가
					new Beat(startTime+gap*186,"S"),    // 3
					new Beat(startTime+gap*186,"J"),    // 추가
					new Beat(startTime+gap*188,"S"),    // 5
					new Beat(startTime+gap*188,"F"),    // 추가
							               
					//변주
					new Beat(startTime+gap*189,"F"),    // 3
					new Beat(startTime+gap*189,"D"),    // 추가
					new Beat(startTime+gap*190,"F"),    // 1
					new Beat(startTime+gap*190,"S"),    // 추가
					new Beat(startTime+gap*191,"S"),    // 4
					new Beat(startTime+gap*191,"F"),    // 추가
					new Beat(startTime+gap*192,"F"),    // 1
					new Beat(startTime+gap*192,"J"),    // 추가
							               
					new Beat(startTime+gap*194,"D"),    // 5
					new Beat(startTime+gap*194,"K"),    // 추가
					new Beat(startTime+gap*196,"L"),    // 2
					new Beat(startTime+gap*196,"S"),    // 추가
					new Beat(startTime+gap*197,"S"),    // 5
					new Beat(startTime+gap*197,"F"),    // 추가
					new Beat(startTime+gap*199,"S"),    // 1
					new Beat(startTime+gap*199,"F"),    // 추가
							               
					new Beat(startTime+gap*203,"F"),    // 3
					new Beat(startTime+gap*203,"D"),    // 추가
					new Beat(startTime+gap*204,"J"),    // 2
					new Beat(startTime+gap*204,"S"),    // 추가
					new Beat(startTime+gap*205,"S"),    // 1
					new Beat(startTime+gap*205,"F"),    // 추가
					new Beat(startTime+gap*206,"D"),    // 2
					new Beat(startTime+gap*206,"K"),    // 추가
							               
					new Beat(startTime+gap*207,"L"),    // 5
					new Beat(startTime+gap*207,"F"),    // 추가
					new Beat(startTime+gap*211,"F"),    // 4
					new Beat(startTime+gap*211,"D"),    // 추가
					new Beat(startTime+gap*213,"S"),    // 5(3)
					new Beat(startTime+gap*213,"F"),    // 추가
					new Beat(startTime+gap*215,"F"),    // 3
					new Beat(startTime+gap*215,"J"),    // 추가
							               
					new Beat(startTime+gap*216,"F"),    // 1
					new Beat(startTime+gap*216,"K"),    // 추가
					new Beat(startTime+gap*217,"S"),    // 추가
					new Beat(startTime+gap*218,"L"),    // 추가
					new Beat(startTime+gap*219,"F"),    // 추가
							               
					new Beat(startTime+gap*223,"L"),    // 2
					new Beat(startTime+gap*223,"D"),    // 추가
					new Beat(startTime+gap*224,"S"),    // 5
					new Beat(startTime+gap*224,"F"),    // 추가
					new Beat(startTime+gap*226,"S"),    // 1
					new Beat(startTime+gap*226,"F"),    // 추가
							               
					new Beat(startTime+gap*228,"F"),    // 3
					new Beat(startTime+gap*228,"D"),    // 추가
					new Beat(startTime+gap*229,"J"),    // 2
					new Beat(startTime+gap*229,"S"),    // 추가
					new Beat(startTime+gap*230,"S"),    // 1
					new Beat(startTime+gap*230,"F"),    // 추가
					new Beat(startTime+gap*231,"D"),    // 2
					new Beat(startTime+gap*231,"K"),    // 추가
							               
					new Beat(startTime+gap*233,"L"),    // 5
					new Beat(startTime+gap*233,"F"),    // 추가
					new Beat(startTime+gap*235,"F"),    // 4
					new Beat(startTime+gap*235,"D"),    // 추가
					new Beat(startTime+gap*236,"S"),    // 5(3)
					new Beat(startTime+gap*236,"F"),    // 추가
					new Beat(startTime+gap*238,"F"),    // 3
					new Beat(startTime+gap*238,"J"),    // 추가
							               
					new Beat(startTime+gap*239,"F"),    // 1
					new Beat(startTime+gap*239,"K"),    // 추가
					new Beat(startTime+gap*240,"S"),    // 추가
					new Beat(startTime+gap*241,"L"),    // 추가
					new Beat(startTime+gap*242,"F"),    // 추가
					new Beat(startTime+gap*243,"J"),    // 추가
							               
					new Beat(startTime+gap*246,"L"),    // 2
					new Beat(startTime+gap*246,"S"),    // 추가
					new Beat(startTime+gap*247,"S"),    // 5
					new Beat(startTime+gap*247,"F"),    // 추가
					new Beat(startTime+gap*248,"S"),    // 1
					new Beat(startTime+gap*248,"F"),    // 추가
							               
					new Beat(startTime+gap*250,"F"),    // 3
					new Beat(startTime+gap*250,"D"),    // 추가
					new Beat(startTime+gap*251,"J"),    // 2
					new Beat(startTime+gap*251,"S"),    // 추가
					new Beat(startTime+gap*252,"S"),    // 1
					new Beat(startTime+gap*252,"F"),    // 추가
					new Beat(startTime+gap*253,"D"),    // 2
					new Beat(startTime+gap*253,"K"),    // 추가
							               
					new Beat(startTime+gap*255,"D"),    // 5
					new Beat(startTime+gap*255,"L"),    // 추가
					new Beat(startTime+gap*256,"K"),    // 5(3)
					new Beat(startTime+gap*256,"J"),    // 추가
					new Beat(startTime+gap*257,"D"),    // 5
					new Beat(startTime+gap*257,"F"),    // 추가
					new Beat(startTime+gap*258,"K"),    // 5(3)
					new Beat(startTime+gap*258,"L"),    // 추가
							               
					new Beat(startTime+gap*260,"S"),    // 5
					new Beat(startTime+gap*260,"F"),    // 추가
					new Beat(startTime+gap*261,"F"),    // 4
					new Beat(startTime+gap*261,"D"),    // 추가
					new Beat(startTime+gap*262,"J"),    // 5(3)
					new Beat(startTime+gap*262,"S"),    // 추가
					new Beat(startTime+gap*263,"L"),    // 3
					new Beat(startTime+gap*263,"F"),    // 추가

					new Beat(startTime+gap*267,"F"),    // 3
					new Beat(startTime+gap*267,"D"),    // 추가
					new Beat(startTime+gap*268,"F"),    // 1
					new Beat(startTime+gap*268,"S"),    // 추가
					new Beat(startTime+gap*269,"S"),    // 4
					new Beat(startTime+gap*269,"F"),    // 추가
					new Beat(startTime+gap*270,"F"),    // 1
					new Beat(startTime+gap*270,"K"),    // 추가
					new Beat(startTime+gap*272,"D"),    // 5
					new Beat(startTime+gap*272,"L"),    // 추가

					new Beat(startTime+gap*276,"L"),    // 2
					new Beat(startTime+gap*276,"S"),    // 추가
					new Beat(startTime+gap*278,"S"),    // 5
					new Beat(startTime+gap*278,"F"),    // 추가
					new Beat(startTime+gap*281,"S"),    // 1
					new Beat(startTime+gap*281,"F"),    // 추가

					new Beat(startTime+gap*285,"Space"),// 3
					new Beat(startTime+gap*285,"D"),    // 추가
					new Beat(startTime+gap*289,"S"),    // 2
					new Beat(startTime+gap*289,"J"),    // 추가
					new Beat(startTime+gap*290,"D"),    // 1
					new Beat(startTime+gap*290,"K"),    // 추가
					new Beat(startTime+gap*291,"F"),    // 2
					new Beat(startTime+gap*291,"L"),    // 추가
					new Beat(startTime+gap*292,"D"),    // 5
					new Beat(startTime+gap*292,"S"),    // 추가

					new Beat(startTime+gap*298,"S"),  // 4
					new Beat(startTime+gap*298,"F"),  // 추가
					new Beat(startTime+gap*299,"L"),  // 5
					new Beat(startTime+gap*299,"K"),  // 추가
					new Beat(startTime+gap*300,"S"),  // 3
					new Beat(startTime+gap*300,"J"),  // 추가

					new Beat(startTime+gap*306,"F"),    // 3
					new Beat(startTime+gap*306,"D"),    // 추가
					new Beat(startTime+gap*307,"F"),    // 1
					new Beat(startTime+gap*307,"S"),    // 추가
					new Beat(startTime+gap*308,"S"),    // 4
					new Beat(startTime+gap*308,"F"),    // 추가
					new Beat(startTime+gap*309,"F"),    // 1
					new Beat(startTime+gap*309,"J"),    // 추가
					new Beat(startTime+gap*310,"D"),    // 5
					new Beat(startTime+gap*310,"K"),    // 추가

					new Beat(startTime+gap*314,"L"),    // 2
					new Beat(startTime+gap*314,"S"),    // 추가
					new Beat(startTime+gap*316,"S"),    // 5
					new Beat(startTime+gap*316,"F"),    // 추가
					new Beat(startTime+gap*319,"S"),    // 1
					new Beat(startTime+gap*319,"F"),    // 추가

					new Beat(startTime+gap*323,"J"),// 3
					new Beat(startTime+gap*323,"D"),    // 추가
					new Beat(startTime+gap*324,"S"),    // 2
					new Beat(startTime+gap*324,"K"),    // 추가
					new Beat(startTime+gap*325,"D"),    // 1
					new Beat(startTime+gap*325,"L"),    // 추가
					new Beat(startTime+gap*326,"F"),    // 2
					new Beat(startTime+gap*326,"S"),    // 추가
					new Beat(startTime+gap*327,"D"),    // 5
					new Beat(startTime+gap*327,"F"),    // 추가

					new Beat(startTime+gap*332,"S"),  // 4
					new Beat(startTime+gap*332,"J"),  // 추가
					new Beat(startTime+gap*335,"L"),  // 5
					new Beat(startTime+gap*335,"K"),  // 추가
					new Beat(startTime+gap*338,"S"),  // 3
					new Beat(startTime+gap*338,"D"),  // 추가

					new Beat(startTime+gap*350,"Space"),  // 3
					new Beat(startTime+gap*350,"F"),  // 추가
					new Beat(startTime+gap*350,"J"),  // 추가
					new Beat(startTime+gap*350,"K"),  // 추가
					new Beat(startTime+gap*350,"L")  // 추가



			};
		}
		else if(titleName.equals("Pontain")&&difficulty.equals("Easy")) {
	         int startTime=1000-Main.REACH_TIME*1000;
	         int gap=333;
	         beats=new Beat[] {
	                 new Beat(startTime + gap * 6, "Space"),  // 1
	                 new Beat(startTime + gap * 12, "K"),      // 2
	                 new Beat(startTime + gap * 16, "J"),     // 3
	                 new Beat(startTime + gap * 19, "S"),     // 4
	                 new Beat(startTime + gap * 27, "F"),
	                 new Beat(startTime + gap * 29, "J"),
	                 new Beat(startTime + gap * 31, "K"),
	                 new Beat(startTime + gap * 33, "L"),
	                 new Beat(startTime + gap * 35, "K"),
	                 new Beat(startTime + gap * 37, "J"),
	                 new Beat(startTime + gap * 39, "D"),
	                 new Beat(startTime + gap * 41, "K"),
	                 new Beat(startTime + gap * 43, "J"),
	                 new Beat(startTime + gap * 49, "F"),
	                 
	                 new Beat(startTime + gap * 55, "Space"),
	                 new Beat(startTime + gap * 58, "K"),
	                 new Beat(startTime + gap * 62, "J"),
	                 new Beat(startTime + gap * 65, "D"),
	                 new Beat(startTime + gap * 68, "F"),
	                 new Beat(startTime + gap * 74, "S"),
	                 
	                 new Beat(startTime + gap * 76, "D"),
	                 new Beat(startTime + gap * 78, "S"),
	                 new Beat(startTime + gap * 80, "K"),
	                 new Beat(startTime + gap * 82, "K"),
	                 new Beat(startTime + gap * 84, "D"),
	                 new Beat(startTime + gap * 86, "D"),
	                 new Beat(startTime + gap * 88, "L"),
	                 new Beat(startTime + gap * 92, "L"),
	                 new Beat(startTime + gap * 95, "S"),
	                 new Beat(startTime + gap * 102, "Space"),
	                 
	                 new Beat(startTime + gap * 111, "S"),
	                 new Beat(startTime + gap * 113, "K"),
	                 new Beat(startTime + gap * 114, "D"),
	                 
	                 new Beat(startTime + gap * 115, "D"),
	                 new Beat(startTime + gap * 116, "L"),
	                 new Beat(startTime + gap * 117, "J"),
	                 
	                 new Beat(startTime + gap * 118, "J"),
	                 new Beat(startTime + gap * 119, "K"),
	                 new Beat(startTime + gap * 120, "K"),
	                 
	                 new Beat(startTime + gap * 123, "S"),
	                 new Beat(startTime + gap * 124, "K"),
	                 new Beat(startTime + gap * 125, "D"),
	                 new Beat(startTime + gap * 126, "D"),
	                 new Beat(startTime + gap * 127, "D"),
	                 new Beat(startTime + gap * 128, "K"),
	                 new Beat(startTime + gap * 129, "K"),
	                 new Beat(startTime + gap * 130, "K"),
	                 new Beat(startTime + gap * 131, "J"),
	                 new Beat(startTime + gap * 134, "F"),
	                   
	                
	         };
	      }
		else if(titleName.equals("Pontain")&&difficulty.equals("Hard")) {
			int startTime=1000-Main.REACH_TIME*1000;
			beats=new Beat[] {
					new Beat(startTime,"Space"),
			};
		}
		else if(titleName.equals("Rubia")&&difficulty.equals("Easy")) {
			int startTime=1000-Main.REACH_TIME*1000;
			beats=new Beat[] {
					new Beat(startTime,"Space"),
			};
		}
		else if(titleName.equals("Rubia")&&difficulty.equals("Hard")) {
			int startTime=1000-Main.REACH_TIME*1000;
			beats=new Beat[] {
					new Beat(startTime,"Space"),
			};
		}
		else if(titleName.equals("Always with me")&&difficulty.equals("Easy")) {
			int startTime=1000-Main.REACH_TIME*1000;
			beats=new Beat[] {
					new Beat(startTime,"Space"),
			};
		}
		else if(titleName.equals("Always with me")&&difficulty.equals("Hard")) {
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
				girlImage=new ImageIcon(Main.class.getResource("/images/girlMiss.png")).getImage();
				score -= 10;
				oxygen -= 10; // 산소 감소
		        if (oxygen < 0) {
		            oxygen = 0; // 산소가 0 이하로 내려가지 않게 조정
		        }
				
			}else if(judge.equals("Late")) {
				judgeImage=new ImageIcon(Main.class.getResource("/images/judgeLate.png")).getImage();
				girlImage=new ImageIcon(Main.class.getResource("/images/girlMiss.png")).getImage();
				score += 5;
				 oxygen += 5; // 산소 증가
			        if (oxygen > 100) {
			            oxygen = 100; // 산소가 100 이상으로 올라가지 않게 조정
			        }
			}else if(judge.equals("Good")) {
				judgeImage=new ImageIcon(Main.class.getResource("/images/judgeGood.png")).getImage();
				girlImage=new ImageIcon(Main.class.getResource("/images/girlGood.png")).getImage();
				
				score += 20;
				 oxygen += 20; // 산소 증가
			        if (oxygen > 100) {
			            oxygen = 100; // 산소가 100 이상으로 올라가지 않게 조정
			        }
			}else if(judge.equals("Great")) {
				judgeImage=new ImageIcon(Main.class.getResource("/images/judgeGreat.png")).getImage();
				girlImage=new ImageIcon(Main.class.getResource("/images/girlGood.png")).getImage();
				
				score += 30;
				 oxygen += 30; // 산소 증가
			        if (oxygen > 100) {
			            oxygen = 100; // 산소가 100 이상으로 올라가지 않게 조정
			        }
			}else if(judge.equals("Early")) {
				judgeImage=new ImageIcon(Main.class.getResource("/images/judgeEarly.png")).getImage();
				girlImage=new ImageIcon(Main.class.getResource("/images/girlGood.png")).getImage();
				score += 10;
				 oxygen += 10; // 산소 증가
			        if (oxygen > 100) {
			            oxygen = 100; // 산소가 100 이상으로 올라가지 않게 조정
			        }
			}else if(judge.equals("Perfect")) {
				judgeImage=new ImageIcon(Main.class.getResource("/images/judgePerfect.png")).getImage();
				girlImage=new ImageIcon(Main.class.getResource("/images/girlGood.png")).getImage();
				
				score += 50;
				 oxygen += 50; // 산소 증가
			        if (oxygen > 100) {
			            oxygen = 100; // 산소가 100 이상으로 올라가지 않게 조정
			        }
			}
		}
}
