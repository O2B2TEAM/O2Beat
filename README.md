### O2beat

## 1차 완성
SQL score 자료형 -> number로 변경하셔야 합니다.
![image](https://github.com/O2B2TEAM/O2Beat/assets/112530099/8dd67284-829a-4781-8992-f90b3a7be316)

## 데이터베이스 -> 포트번호, SCORE 확인 해 주세요.

데이터베이스 : Oracle
버전 : 19c
전역 데이터베이스 이름 : orcl
SID : orcl
포트번호 : 1521 (GH)
포트번호 : 1522 (MJ)

데이터베이스 사용자 이름 : java
데이터베이스 비밀번호 : oracle

SCORES 테이블
- USERNAME => USERS의 외래키
- SCORE => VARCHAR2 (50 BYTE) -> NUMBER 로 변경

USERS 테이블
- USERNAME => VARCHAR2 (50 BYTE)
- PASSWORD => VARCHAR2 (50 BYTE)



## 폴더 구조
![image](https://github.com/O2B2TEAM/O2Beat/assets/112530099/e5111c13-12d3-45ae-86f8-cd90b3c4fec0)


## jar 파일 경로
프로젝트파일-buildpath-configurebuildpath-classpath 에 jlayer.jar(mp3플레이라이브러리) 넣어야해요

![image](https://github.com/O2B2TEAM/O2Beat/assets/112530099/b9bf6f6e-9a0f-42b2-b7c0-b76a4d6768df)

# 게임 플레이 시간 제한

DynamicBeat.java

public DynamicBeat() 생성자 최상단에 trackList 맨 끝을 보면 숫자가 적혀있습니다. 1초가 1000 입니다.

trackList.add(new Track("music1TitleImage.png", "music1StartImage.jpg", "music1GameImage.jpg",
				"music1_piSongSelected.mp3", "music1_piSong.mp3","music1_piSong",30000)); //분




## 노트찍기
#pi song 음표 순, 마디 순으로 enter 해 두었습니다. 스마트폰 타입랩스 기능 이용해서 gap 뒤에 *곱한 수 조정하다 보면 금방 합니다.
enter 기준으로 첫번째 문장의 gap에  곱하는 수만 고치면 그 다음 문장들은 규칙이 있어서 금방 고쳐요

Game.java

int startTime=1920-Main.REACH_TIME*1000; //첫 비트 떨어지는 시간 약 1.92초
			int gap=333; // 3/4박자, 4/4박자: 250 , 1/8 박자일 경우 125)
			beats=new Beat[] {
					new Beat(startTime+gap*2,"Space"),     // (3141)5
					
					new Beat(startTime+gap*8,"K"),     // 2
					new Beat(startTime+gap*10,"J"),    // 5(1)
					
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
					
					new Beat(startTime+gap*112,"L"),    // 2
					new Beat(startTime+gap*113,"S"),    // 5
					new Beat(startTime+gap*115,"S"),    // 1
					
					new Beat(startTime+gap*121,"Space"),// 3
					new Beat(startTime+gap*122,"S"),    // 2
					new Beat(startTime+gap*123,"D"),    // 1
					new Beat(startTime+gap*124,"F"),    // 2
					new Beat(startTime+gap*125,"D"),    // 5

					new Beat(startTime+gap*130,"S"),    // 4
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
					new Beat(startTime+gap*190,"F"),    // 3
					new Beat(startTime+gap*191,"F"),    // 1
					new Beat(startTime+gap*192,"S"),    // 4
					new Beat(startTime+gap*193,"F"),    // 1
					
					new Beat(startTime+gap*194,"D"),    // 5
					new Beat(startTime+gap*198,"L"),    // 2
					new Beat(startTime+gap*199,"S"),    // 5
					new Beat(startTime+gap*201,"S"),    // 1
					
					new Beat(startTime+gap*203,"F"),    // 3
					new Beat(startTime+gap*204,"J"),    // 2
					new Beat(startTime+gap*205,"S"),    // 1
					new Beat(startTime+gap*206,"D"),    // 2
					
					new Beat(startTime+gap*207,"L"),    // 5
					new Beat(startTime+gap*211,"F"),    // 4
					new Beat(startTime+gap*213,"S"),    // 5(3)
					new Beat(startTime+gap*215,"F"),    // 3
					
					new Beat(startTime+gap*216,"F"),    // 1

