# <img src="https://lab.ssafy.com/s08-webmobile1-sub2/S08P12D206/-/raw/main/img/App_logo.png" width="30" height="30"> Last Market -진평동 인력사무소


##  개요 

실시간으로 나의 라이프스타일을 공유하고 라이프 스타일이 담긴 물건을 실시간 경매로 사고 팔 수 있는 중고거래 플랫폼



## 1️⃣프로젝트 정보
### 🏆Contributor

    - Back-End Developer
        - 김민재(팀장)
            Infra, WebRTC, 실시간 채팅 기능 담당
        - 박희종
            Rest API, 인증/인가 담당
    - Web Front-End Developer
        - 김유홍
            라이프스타일 큐레이팅 담당(CRUD & UI)
        - 최종현
            WEBRTC, 채팅 담당
    - Android Devloper 
        - 권주현
            안드로이드 개발 전체 담당

### 💻기술 스택 (사용한 기술)

| Web-Front-End | Web-Back-End |Android|
| ------ | ------ |------|
|React      |  RDS      |Android Studio (Kotlin, Java)|
|Styled-Component       |Redis        |Retrofit(API통신)|
||Spring Boot|OkHTTP(OpenVidu)|
||Spring Data JPA|Stomp Library(Chat)|
||Spring Security|MVVM pattern|
||AWS EC2|Material Design3|
||Nginx||
||Docker||
||Jenkis||
||S3||

### ✏프로젝트 관리 툴
    - Jira
    - Notion
    - MatterMost(배포 봇)
    - Gitlab

### 프로젝트 산출물
[모바일-피그마(목업)](https://www.figma.com/file/gJQVvVN69heeWQYL7QpFdE/Last-Market?node-id=25%3A1464&t=TnSzDhmvaKQq2x9p-1)

[웹-피그마(목업)](https://www.figma.com/file/8hCQJ9g4rNOpxAY3AAg7HL/%EB%AA%A9%EC%97%85-%ED%8E%98%EC%9D%B4%EC%A7%80?node-id=0%3A1)

[기능 명세서](https://www.notion.so/limecats0331/72e0b6488f7541219b15464abb3d400d?pvs=4)

[API 명세서](https://www.notion.so/limecats0331/7374a4a9383f463c8e37d9dbc69a90c4?v=43535742297546548235857925907114&pvs=4)

[아키텍쳐](https://www.notion.so/limecats0331/02b772780a5a4f21a004b3ec1430e8e9)

[최종 발표 PPT](https://drive.google.com/file/d/1hBAKpBlogGMpi64HChva13YIrAvDqBa3/view?usp=sharing)

# 2️⃣기능 설명
## 기본기능
카카오, 네이버 로그인
사용자 위치 기반 
상품 CRUD 
카테고리별 물품 Sorting(최신순,찜순,라이브중인것만)
검색
마이페이지(찜목록,리뷰목록,판매상품목록,구매상품목록)

## Live 경매 기능
- 라이브 날짜와 시간을 입력하여 Live 예정 상품을 등록할 수 있다.
- 호스트가 라이브를 시작하면 실시간으로 호스트의 화면이 송출되게 되고 참여한 참여자에게 보여지게 된다.
- 라이브를 진행하면서 실시간으로 궁금한 점을 채팅을 통해 전달 할 수 있다.
- 구매자는 가격의 10%씩 올리면서 호가를 부를 수 있고 판매자는 더이상 호가가 없으면 해당 가격으로 낙찰을 진행 할 수 있다. 
- openvidu(webRTC)와 WebSocket(Stomp)통신을 사용하여 구현되어있다.

## 채팅 기능
-  라이브에서 구매자 본인의 호가가 최고가로서 낙찰한 경우 또는 즉시 구매버튼을 눌렀을 경우 채팅 페이지로 넘어간다.
- 채팅을 주고받으며 거래장소와 시간을 정하고 거래완료 버튼을 눌렀을 때 리뷰를 남길 수 있다.
- WebSocket 통신을 사용하여 구현되어있다.

[화면 설명서-웹](https://docs.google.com/presentation/d/1b_4iXUtIA4F08hrXCy_Ubf8PYQ2QFtVf/edit?usp=share_link&ouid=113136065755976595387&rtpof=true&sd=true) 

[화면 설명서-모바일](https://docs.google.com/presentation/d/1IktfAI5ynyiodvc0Zac6WZf_Izmf28Ps/edit?usp=share_link&ouid=113136065755976595387&rtpof=true&sd=true)
