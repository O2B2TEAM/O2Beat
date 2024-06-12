package dynamic_beat_42;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics; //ctrl+shit+O 자동 임포트
import java.awt.Graphics2D;
import java.awt.Image; // 더블버퍼링 기법. 버퍼에 이미지를 담아 매 순간 이미지 갱신
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame; //GUI
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DynamicBeat extends JFrame {
//전역 변수 정리
	// 더블버퍼링으로 화면 전체 이미지를 담는 인스턴스
	private Image screenImage;
	private Graphics screenGraphic;
	// 버튼 이미지 인스턴스
	// 메뉴바 프로그램 종료 버튼
	private ImageIcon exitButtonEnteredImage = new ImageIcon(Main.class.getResource("/images/exitButtonEntered.png"));
	private ImageIcon exitButtonBasicImage = new ImageIcon(Main.class.getResource("/images/exitButtonBasic.png"));
	// 게임시작버튼
	private ImageIcon startButtonEnteredImage = new ImageIcon(Main.class.getResource("/images/startButtonEntered.png"));
	private ImageIcon startButtonBasicImage = new ImageIcon(Main.class.getResource("/images/startButtonBasic.png"));
	// 게임종료버튼
	private ImageIcon quitButtonEnteredImage = new ImageIcon(Main.class.getResource("/images/quitButtonEntered.png"));
	private ImageIcon quitButtonBasicImage = new ImageIcon(Main.class.getResource("/images/quitButtonBasic.png"));
	// 난이도선택 좌우버튼
	private ImageIcon leftButtonEnteredImage = new ImageIcon(Main.class.getResource("/images/leftButtonEntered.png"));
	private ImageIcon leftButtonBasicImage = new ImageIcon(Main.class.getResource("/images/leftButtonBasic.png"));
	private ImageIcon rightButtonEnteredImage = new ImageIcon(Main.class.getResource("/images/rightButtonEntered.png"));
	private ImageIcon rightButtonBasicImage = new ImageIcon(Main.class.getResource("/images/rightButtonBasic.png"));
	// 모드선택 easyHard버튼
	private ImageIcon easyButtonEnteredImage = new ImageIcon(Main.class.getResource("/images/easyButtonEntered.png"));
	private ImageIcon easyButtonBasicImage = new ImageIcon(Main.class.getResource("/images/easyButtonBasic.png"));
	private ImageIcon hardButtonEnteredImage = new ImageIcon(Main.class.getResource("/images/hardButtonEntered.png"));
	private ImageIcon hardButtonBasicImage = new ImageIcon(Main.class.getResource("/images/hardButtonBasic.png"));
	// 뒤로가기 버튼
	private ImageIcon backButtonEnteredImage = new ImageIcon(Main.class.getResource("/images/backButtonEntered.png"));
	private ImageIcon backButtonBasicImage = new ImageIcon(Main.class.getResource("/images/backButtonBasic.png"));
	// 배경이미지 인스턴스
	private Image background = new ImageIcon(Main.class.getResource("/images/introBackground(Title).jpg")).getImage();
	// 메뉴바 인스턴스
	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("/images/menuBar.png")));
	// 버튼 인스턴스
	private JButton exitButton = new JButton(exitButtonBasicImage); // exit 버튼
	private JButton startButton = new JButton(startButtonBasicImage); // start 버튼
	private JButton quitButton = new JButton(quitButtonBasicImage); // quit 버튼
	private JButton leftButton = new JButton(leftButtonBasicImage); // left 버튼
	private JButton rightButton = new JButton(rightButtonBasicImage); // right 버튼
	private JButton easyButton = new JButton(easyButtonBasicImage); // easy 버튼
	private JButton hardButton = new JButton(hardButtonBasicImage); // hard 버튼
	private JButton backButton = new JButton(backButtonBasicImage); // back 버튼
	private JButton logoutButton = new JButton("로그아웃"); // logout 버튼
	
//코드를 위한 전역 변수 설정
	private int mouseX, mouseY; // 실행되는 프로그램 안에서의 마우스 x좌표와 y좌표

	// 메인화면 공개여부
	private boolean isMainScreen = false;
	// 현재 게임화면으로 넘어왔는지에 대한 변수
	private boolean isGameScreen = false;

	// 곡 선택
	ArrayList<Track> trackList = new ArrayList<Track>();
	// 타이틀 이미지 인스턴스
	private Image titleImage;
	// 음악 선택 이미지 인스턴스
	private Image selectedImage;
	// 음악 선택 mp3 인스턴스
	private Music selectedMusic;
	// 프로그램 시작 시 인트로 음악 재생
	private Music introMusic = new Music("introMusic.mp3", true);
	// 현재 선택된 곡 번호
	private int nowSelected = 0; // 인덱스
	
	private String username;

	public static Game game;
	//최종 점수 화면
	//private Image scoreResult = new ImageIcon(getClass().getResource("/images/scoreResult.png")).getImage();
	private boolean isResult = false;
	scoreResult scoreResult;
	
	private int highScore;
	private JLabel highScoreLabel;
	
	
//생성자(첫번째로 실행되는 메소드, 대부분 JFrame 사용 시 필수적으로 실행해주어야 하는 메소드입니다.)
	public DynamicBeat(String username, HashMap<String, Integer> scores) {
		this.username=username;
		this.highScore=Database.getHighScore(username);
	    // 로그인 된 사용자 환영 메시지와 현재 점수 출력
        JOptionPane.showMessageDialog(null, username + "님 환영합니다!! ");
		// Track 클래스의 생성자 매개변수 값 5개(String titleImage, String startImage, String
		// gameImage, String startMusic, String gameMusic)
		//로딩 길어지는 것 방지를 위해 메소드 제일 위에 위치
		// 인덱스 0 (add로 trackList에 더하는 순서)
		trackList.add(new Track("music1TitleImage.png", "music1StartImage.jpg", "music1GameImage.jpg",
				"music1_piSongSelected.mp3", "PI Song.mp3","PI Song",119000)); //1000=1초
		// 인덱스 1
		trackList.add(new Track("music2TitleImage.png", "music2StartImage.jpg", "music2GameImage.jpg",
				"music2Selected.mp3", "Pontain.mp3","Pontain",323000));
		// 인덱스 2
		trackList.add(new Track("music3TitleImage.png", "music3StartImage.jpg", "music3GameImage.jpg",
				"music3Selected.mp3", "Rubia.mp3","Rubia",234000));
		// 인덱스 3
		trackList.add(new Track("music4TitleImage.png", "music4StartImage.jpg", "music4GameImage.jpg",
				"music4Selected.mp3", "Always with me.mp3","Always with me",300000));
		// 원래 자바에서 제공하는 메뉴바사라짐
		setUndecorated(true);
		// 메뉴바 제목
		setTitle("O2Beat");
		// 창 크기
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		// 창 크기 불가변
		setResizable(false);
		// 상대적 위치 값
		setLocationRelativeTo(null);
		// 게임창 종료 시 프로그램 종료
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 화면 출력(기본값이 false 이므로 코드 작성해주어야 합니다.)
		setVisible(true);
		// 기본 색상 회색이 아닌 흰색으로
		setBackground(new Color(0, 0, 0, 0));
		// 메뉴바를 drag하면 창이 따라서 움직여야 하는 기능을 따로 구현해주어야 하기 때문에 위치 값을 null로 합니다.
		setLayout(null);
		//keyListener 이벤트
		addKeyListener(new KeyListener());
		// 프로그램 시작 시 인트로음악 재생
		introMusic.start();
		
        // 추가: 사용자 이름과 최고 점수를 표시할 JLabel 생성
		highScoreLabel = new JLabel("<html>" + username + "님<br>최고점수:" + highScore + "</html>", SwingConstants.CENTER);
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 80));
        highScoreLabel.setBounds(410, 300, 500, 500); // 크기를 넓게 설정
        highScoreLabel.setForeground(Color.WHITE); // 글씨 색상 설정
        add(highScoreLabel);
        highScoreLabel.setVisible(true); // 처음에는 보이지 않도록 설정


// exit 버튼 메소드 (프로그램 종료)
		// 버튼 위치 설정
		exitButton.setBounds(1245, 0, 30, 30);
		// 이클립스 제공 종료버튼 프레임 배경 선 제거
		exitButton.setBorderPainted(false);
		// 이클립스 제공 종료버튼 프레임 배경 색 제거
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		// 마우스 이벤트
		exitButton.addMouseListener(new MouseAdapter() {
			// hover 이벤트
			@Override
			public void mouseEntered(MouseEvent e) {
				// hover 시 이미지 변경
				exitButton.setIcon(exitButtonEnteredImage);
				// hover 시 커서 모양 변경
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// hover 시 음악 재생
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}

			// 마우스가 올라갔다가 나왔을 때 이벤트
			@Override
			public void mouseExited(MouseEvent e) {
				// 이미지 변경
				exitButton.setIcon(exitButtonBasicImage);
				// 커서 변경
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			// click 이벤트 (프로그램 종료)
			@Override
			public void mousePressed(MouseEvent e) {
				// 음악 재생
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				// 음악이 나오자마자 종료되기 때문에 try-catch 사용
				try {
					buttonPressedMusic.start();
					Thread.sleep(1000); // 1초
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(exitButton); // JFrame 사용 시 작성해주어야 하는 코드

// start 버튼 메소드(게임 시작)
		// 버튼 위치 지정 (x좌표,y좌표,x축 크기, y축 크기)
		startButton.setBounds(1000, 400, 200, 200);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			// 마우스 hover 시
			@Override
			public void mouseEntered(MouseEvent e) {
				// hover 시 이미지 변경
				startButton.setIcon(startButtonEnteredImage);
				// hover 시 커서 이미지 변경
				startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
				// hover 시 음악 재생
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}

			// 마우스 hover했다가 나왔을 때
			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 올라갔다가 나왔을 때
				startButton.setIcon(startButtonBasicImage);// 마우스가 올라갔다가 나왔을 때
				startButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 마우스 원래 모양으로 돌아감
			}

			// 버튼 눌렀을 때(게임 시작)
			@Override
			public void mousePressed(MouseEvent e) {
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				buttonPressedMusic.start();
				enterMain();
			}
		});
		add(startButton); // JFrame에 종료버튼 추가

		// quit버튼 메소드
		quitButton.setBounds(100, 400, 200, 200); // 버튼 위치 지정 (x,y, x축크기,y축크기)
		quitButton.setBorderPainted(false); // 기존 종료버튼 프레임 배경 선 제거
		quitButton.setContentAreaFilled(false); // 기존 종료버튼 프레임 배경 색 제거
		quitButton.setFocusPainted(false);
		quitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // hover 상태에서의 이벤트 처리
				quitButton.setIcon(quitButtonEnteredImage);// 마우스 올라갔을 때 변경
				quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스 올라갔을 때 손 모양
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 올라갔다가 나왔을 때
				quitButton.setIcon(quitButtonBasicImage);// 마우스가 올라갔다가 나왔을 때
				quitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 마우스 원래 모양으로 돌아감
			}

			@Override
			public void mousePressed(MouseEvent e) { // 프로그램 종료(exit버튼과 동일)
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				// 음악이 나오자마자 종료되기 때문에 try-catch 사용
				try {
					buttonPressedMusic.start();
					Thread.sleep(1000); // 1초
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		add(quitButton); // JFrame에 종료버튼 추가

		// left버튼 메소드
		// default 상태를 보이지 않게 함
		leftButton.setVisible(false);
		leftButton.setBounds(100, 550, 60, 60); // 버튼 위치 지정 (x,y, x축크기,y축크기)
		leftButton.setBorderPainted(false); // 기존 종료버튼 프레임 배경 선 제거
		leftButton.setContentAreaFilled(false); // 기존 종료버튼 프레임 배경 색 제거
		leftButton.setFocusPainted(false);
		leftButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // hover 상태에서의 이벤트 처리
				leftButton.setIcon(leftButtonEnteredImage);// 마우스 올라갔을 때 변경
				leftButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스 올라갔을 때 손 모양
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 올라갔다가 나왔을 때
				leftButton.setIcon(leftButtonBasicImage);// 마우스가 올라갔다가 나왔을 때
				leftButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 마우스 원래 모양으로 돌아감
			}

			@Override
			public void mousePressed(MouseEvent e) { // 프로그램 종료(exit버튼과 동일)
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				selectLeft();
			}
		});
		add(leftButton); // JFrame에 종료버튼 추가

		// right버튼 메소드
		// default 상태를 보이지 않게 함
		rightButton.setVisible(false);
		rightButton.setBounds(500, 545, 60, 60); // 버튼 위치 지정 (x,y, x축크기,y축크기)
		rightButton.setBorderPainted(false); // 기존 종료버튼 프레임 배경 선 제거
		rightButton.setContentAreaFilled(false); // 기존 종료버튼 프레임 배경 색 제거
		rightButton.setFocusPainted(false);
		rightButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // hover 상태에서의 이벤트 처리
				rightButton.setIcon(rightButtonEnteredImage);// 마우스 올라갔을 때 변경
				rightButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스 올라갔을 때 손 모양
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 올라갔다가 나왔을 때
				rightButton.setIcon(rightButtonBasicImage);// 마우스가 올라갔다가 나왔을 때
				rightButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 마우스 원래 모양으로 돌아감
			}

			@Override
			public void mousePressed(MouseEvent e) { // 프로그램 종료(exit버튼과 동일)
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				selectRight();
			}
		});
		add(rightButton); // JFrame에 종료버튼 추가

		// easy버튼 메소드
		// default 상태를 보이지 않게 함
		easyButton.setVisible(false);
		easyButton.setBounds(800, 50, 370, 300); // 버튼 위치 지정 (x,y, x축크기,y축크기)
		easyButton.setBorderPainted(false); // 기존 종료버튼 프레임 배경 선 제거
		easyButton.setContentAreaFilled(false); // 기존 종료버튼 프레임 배경 색 제거
		easyButton.setFocusPainted(false);
		easyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // hover 상태에서의 이벤트 처리
				easyButton.setIcon(easyButtonEnteredImage);// 마우스 올라갔을 때 변경
				easyButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스 올라갔을 때 손 모양
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 올라갔다가 나왔을 때
				easyButton.setIcon(easyButtonBasicImage);// 마우스가 올라갔다가 나왔을 때
				easyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 마우스 원래 모양으로 돌아감
			}

			@Override
			public void mousePressed(MouseEvent e) { // 프로그램 종료(exit버튼과 동일)
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				gameStart(nowSelected, "Easy");
			}
		});
		add(easyButton); // JFrame에 종료버튼 추가

		// hard버튼 메소드
		// default 상태를 보이지 않게 함
		hardButton.setVisible(false);
		hardButton.setBounds(800, 300, 370, 300); // 버튼 위치 지정 (x,y, x축크기,y축크기)
		hardButton.setBorderPainted(false); // 기존 종료버튼 프레임 배경 선 제거
		hardButton.setContentAreaFilled(false); // 기존 종료버튼 프레임 배경 색 제거
		hardButton.setFocusPainted(false);
		hardButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // hover 상태에서의 이벤트 처리
				hardButton.setIcon(hardButtonEnteredImage);// 마우스 올라갔을 때 변경
				hardButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스 올라갔을 때 손 모양
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 올라갔다가 나왔을 때
				hardButton.setIcon(hardButtonBasicImage);// 마우스가 올라갔다가 나왔을 때
				hardButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 마우스 원래 모양으로 돌아감
			}

			@Override
			public void mousePressed(MouseEvent e) { // 프로그램 종료(exit버튼과 동일)
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				gameStart(nowSelected, "Hard");
			}
		});
		add(hardButton); // JFrame에 종료버튼 추가

		// back버튼 메소드
		// default 상태를 보이지 않게 함
		backButton.setVisible(false);
		backButton.setBounds(20, 50, 60, 60); // 버튼 위치 지정 (x,y, x축크기,y축크기)
		backButton.setBorderPainted(false); // 기존 종료버튼 프레임 배경 선 제거
		backButton.setContentAreaFilled(false); // 기존 종료버튼 프레임 배경 색 제거
		backButton.setFocusPainted(false);
		backButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) { // hover 상태에서의 이벤트 처리
				backButton.setIcon(backButtonEnteredImage);// 마우스 올라갔을 때 변경
				backButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스 올라갔을 때 손 모양
				Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
				buttonEnteredMusic.start();
			}

			@Override
			public void mouseExited(MouseEvent e) { // 마우스가 올라갔다가 나왔을 때
				backButton.setIcon(backButtonBasicImage);// 마우스가 올라갔다가 나왔을 때
				backButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 마우스 원래 모양으로 돌아감
			}

			@Override
			public void mousePressed(MouseEvent e) { // 프로그램 종료(exit버튼과 동일)
				Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
				backMain();
			}
		});
		add(backButton); // JFrame에 종료버튼 추가
		// 로그아웃 버튼
				logoutButton.setVisible(false);
				logoutButton.setBounds(900, 590, 200, 50); // 버튼 위치 지정 (x,y, x축크기, y축크기)
//				logoutButton.setBorderPainted(false); // 기존 종료버튼 프레임 배경 선 제거
//				logoutButton.setContentAreaFilled(false); // 기존 종료버튼 프레임 배경 색 제거
//				logoutButton.setFocusPainted(false);
				logoutButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) { // hover 상태에서의 이벤트 처리
//						logoutButton.setIcon(logoutButtonEnteredImage);// 마우스 올라갔을 때 변경
						logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스 올라갔을 때 손 모양
						Music buttonEnteredMusic = new Music("buttonEnteredMusic.mp3", false);
						buttonEnteredMusic.start();
					}

					@Override
					public void mouseExited(MouseEvent e) { // 마우스가 올라갔다가 나왔을 때
//						logoutButton.setIcon(logoutButtonBasicImage);// 마우스가 올라갔다가 나왔을 때
						logoutButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // 마우스 원래 모양으로 돌아감
					}

					@Override
					public void mousePressed(MouseEvent e) { // 프로그램 종료(exit버튼과 동일)
						Music buttonPressedMusic = new Music("buttonPressedMusic.mp3", false);
						logout();
					}
				});
				add(logoutButton); // JFrame에 종료버튼 추가

		// 메뉴바 구현
		menuBar.setBounds(0, 0, 1280, 30); // setbound로 위치와 크기 설정
		menuBar.addMouseListener(new MouseAdapter() { // 메뉴바에서 마우스 클릭했을 때 x좌표와 y좌표 얻어오기
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) { // 메뉴바에서 드래그 이벤트가 발생했을 때 x,y 좌표
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY); // 메뉴바 드래그했을 때 마우스 커서를 따라오도록 이벤트 처리
			}

		});
		add(menuBar); // Jframe에 menubar 추가

	}

	// 이미지 불러오기
	public void paint(Graphics g) { // JFrame으로 제작된 GUI 형태의 프로그램에서 제일 먼저 이미지를 불러와주는 함수 (약속)
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT); // 다음 크기의 이미지를 만들어 screenImage 인스턴스로
		screenGraphic = screenImage.getGraphics(); // screenImage를 통해 screenGraphic 객체를 얻어옴
		screenDraw((Graphics2D) screenGraphic); // 2D 이미지로 형변환하여 글자깨짐 방지
		g.drawImage(screenImage, 0, 0, null); // 만들어지는 이미지를 띄울 창 위치
	}

	public void screenDraw(Graphics2D g) { // drawImage 함수는 뒤에서 그려질 수록 실제로 이용자가 볼 때는 앞에서 보이게 된다.
		// add가 아니라 일반적으로 보여주고 싶을 때 drawImage 함수
		g.drawImage(background, 0, 0, null);
		// main화면(난이도 선택화면에서만 보여짐)
		if (isMainScreen) {
			// 음악 선택 이미지
			g.drawImage(selectedImage, 150, 100, null);
			// 타이틀 이미지
			g.drawImage(titleImage, 225, 540, null);
			
			scorePanel(g, trackList.get(nowSelected).getTitleName());
		}
		
		if (isGameScreen) {

			game.screenDraw(g);
		}
        // 결과가 true 면 화면 출력
        if(isResult){
            scoreResult = new scoreResult(g, username);
            scoreResult.start();
        }
		

		paintComponents(g); // JFrame에 추가한 JLabel 메소드 사용한 객체를 그려주는 메소드, add() 메소드는 이 부분에 해당됨
		//시간차를 두고 실행될 수 있도록 변경
		try {
			Thread.sleep(5);
		}catch(Exception e) {
			e.printStackTrace();
		}
		// paint 함수를 불러와 매 순간 이미지를 다시 띄움 -> 더블버퍼링 기법
		this.repaint();
	}

	public void selectTrack(int nowSelected) {
		if (selectedMusic != null) {
			selectedMusic.close();
		}
		// 선택된 트랙의 타이틀 이미지를 가져오기
		titleImage = new ImageIcon(Main.class.getResource("/images/" + trackList.get(nowSelected).getTitleImage()))
				.getImage();
		selectedImage = new ImageIcon(Main.class.getResource("/images/" + trackList.get(nowSelected).getStartImage()))
				.getImage();
		selectedMusic = new Music(trackList.get(nowSelected).getStartMusic(), true);
		selectedMusic.start();
	}

	// 곡 선택 왼쪽 버튼 눌렀을 때 메소드
	public void selectLeft() {
		// 인덱스가 0일때 왼쪽을 누르면 전체 트랙리스트에서 1을 뺀 값(마지막 곡)
		if (nowSelected == 0) {
			nowSelected = trackList.size() - 1;
		} else { // 인덱스가 0이 아니면 그 전 인덱스 곡
			nowSelected--;
		}
		selectTrack(nowSelected);
	}

	// 곡 선택 오른쪽 버튼 눌렀을 때 메소드
	public void selectRight() {
		// 트랙리스트의 마지막 곡일때 오른쪽을 누르면 첫번째 인덱스 값 곡
		if (nowSelected == trackList.size() - 1) {
			nowSelected = 0;
		} else { // 마지막 곡이 아니면 다음 인덱스 값 곡
			nowSelected++;
		}
		selectTrack(nowSelected);
	}

	// 게임 화면으로 이동 메소드
	public void gameStart(int nowSelected, String difficulty) { // 인덱스번호, 난이도
		if (selectedMusic != null) { // 음악이 실행중이라면, null값이 아니라면 종료
			selectedMusic.close();
		}
		isMainScreen = false;// 메인 화면이 아님
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		easyButton.setVisible(false);
		hardButton.setVisible(false);
		logoutButton.setVisible(false);
		// 배경 변경
		background = new ImageIcon(Main.class.getResource("/images/" + trackList.get(nowSelected).getGameImage()))
				.getImage();
		backButton.setVisible(true);
		isGameScreen = true;
		//
		game=new Game(trackList.get(nowSelected).getTitleName(),difficulty,trackList.get(nowSelected).getGameMusic(),username);
		game.start();
		//게임창에 키보드포커스를 맞추어주는 메소드
		setFocusable(true);// 키보드 이벤트가 항상 오류없이 실행되도록 메소드 제일 아래 위치
	
		// 게임 시작 시 점수 & 콤보 초기화
        game.score = 0;
        
        // 시간에 따른 작업은 Timer, Timertask 를 통해서 가능
        // TimerTask 를 통해서 작업을 run 메소드 안에 작업 정의 한 후
        // timer.schedule 메소드에 매개변수를 2개 넣는데, task 와 task 를 실행할 시간
        // 이때 schedule 의 시간 매개변수가 5000 이라면 5초 뒤 실행 이라는 의미
        java.util.Timer timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                isResult = true;

            }
        };

        // 234000 ms 후에 run 메소드 실행
        timer.schedule(task, trackList.get(nowSelected).getMusicTime());

    // 키보드 이벤트 동작을 위한 메서드
    // 이는 Main 클래스에 포커스가 맞춰져있어야 키보드 이벤트가 정상적으로 동작하기 때문
    setFocusable(true);
    requestFocus();
    highScoreLabel.setVisible(false); // 게임 화면에서 숨기도록 설정
    }
	

	

	// 뒤로가기 버튼을 눌렀을 때 메소드
	public void backMain() {
	    if (isMainScreen) {
	        // 첫 화면으로 돌아가기
	        isMainScreen = false;
	        leftButton.setVisible(false);
	        rightButton.setVisible(false);
	        easyButton.setVisible(false);
	        hardButton.setVisible(false);
	        logoutButton.setVisible(false);
	        startButton.setVisible(true);
	        quitButton.setVisible(true);
	        highScoreLabel.setVisible(true); // 첫 화면에서 보이도록 설정
	        background = new ImageIcon(Main.class.getResource("/images/introBackground(Title).jpg")).getImage();
	        if (selectedMusic != null) {
	            selectedMusic.close();
	        }
	        introMusic.start();
	    } else {
	        // 메인 화면으로 돌아가기
	        isMainScreen = true;
	        leftButton.setVisible(true);
	        rightButton.setVisible(true);
	        easyButton.setVisible(true);
	        hardButton.setVisible(true);
	        logoutButton.setVisible(true);
	        background = new ImageIcon(Main.class.getResource("/images/mainBackground.jpg")).getImage();
	        backButton.setVisible(true);
	        selectTrack(nowSelected);
	        isGameScreen = false;
	        isResult = false;

	        if (!isResult && scoreResult != null) {
	            scoreResult.close();
	        }
	        game.close();
	        updateHighScoreLabel(); // highScore 라벨 업데이트
	    }
	}


	// 메인 화면에 들어갔을 때 메소드
	public void enterMain() {
		startButton.setVisible(false);
		quitButton.setVisible(false);
		// 배경 변경
		background = new ImageIcon(Main.class.getResource("/images/mainBackground.jpg")).getImage();
		// 메인스크린 함수 활성화
		isMainScreen = true;
		// 시작버튼을 누르면 버튼이 사라짐
		leftButton.setVisible(true);
		rightButton.setVisible(true);
		easyButton.setVisible(true);
		hardButton.setVisible(true);
		logoutButton.setVisible(true);
		// 음악재생
		introMusic.close();
		selectTrack(0);    
		highScoreLabel.setVisible(false); // 메인 화면에서 보이지 않도록 설정
		backButton.setVisible(true); // 항상 보이도록 설정
	}
	
	 private void logout() { //로그아웃 메서드
	        // 로그아웃 처리
			selectedMusic.close();
	        new Login(); // 로그인 화면으로 돌아가기
	        dispose(); // DynamicBeat Frame 종료
	    }
	 	 private void updateHighScoreLabel() {
		    highScore = Database.getHighScore(username); // DB에서 사용자 최고 점수 가져오기
		    highScoreLabel.setText("<html>" + username + "님<br>최고점수:" + highScore + "</html>"); // 라벨 텍스트 업데이트
		}

	
    private void scorePanel(Graphics2D g, String musicName){

        // 색깔 ,폰트 정의
        g.setColor(Color.green);
        g.drawString("SCORE BOARD", 820, 200);
        g.setColor(Color.cyan);

        }
    }

