import React, { Component } from "react";
import { OpenVidu } from "openvidu-browser";
import axios from "axios";
import styled from "styled-components";
import UserVideoComponent from "../Pages/Live/UserVideoComponent";
import VideocamOutlinedIcon from "@mui/icons-material/VideocamOutlined";
import MicOutlinedIcon from "@mui/icons-material/MicOutlined";
import HeadsetIcon from "@mui/icons-material/Headset";
import VideocamOffOutlinedIcon from "@mui/icons-material/VideocamOffOutlined";
import MicOffIcon from "@mui/icons-material/MicOff";
import HeadsetOffIcon from "@mui/icons-material/HeadsetOff";
import CallEndIcon from "@mui/icons-material/CallEnd";
import ChatIcon from "@mui/icons-material/Chat";
import LiveChat from "../Chat"
import WithRouter from "../Components/WithRouter";

// 로컬 미디어 서버 주소
const OPENVIDU_SERVER_URL = "https://i8d206.p.ssafy.io";
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

const Container = styled.div`
  height: 100vh;
  width: 100%;
  background-color: #5D6DBE; // 바탕화면 색
`;

const Header = styled.div`
  height: 8vh;
  display: flex;
  align-items: center;
  padding: 0 50px;
  justify-content: center;
`;

const StudyTitle = styled.p`
  color: white;
  font-size: 20px;
  font-weight: 600;
`;

const Middle = styled.div`
  width: 100%;
  height: 77vh;
  display: flex;
  overflow: hidden;
`;

const Left = styled.div`
  flex: 1;
  width: 100%;
  display: flex;
  justify-content: left;
`;

const Right = styled.div`
  position: relative;
  padding: 0 20px;
  display: flex;
  align-items: center;
  transition: 0.5s;
  ${(props) =>
    props.primary ? `right:0; flex:1;` : `right:calc(-100vw/3); flex:0;`}
`;

const Chat = styled.div`
  width: 100%;
  height: 93%;
  border-radius: 5px;
  background-color: white;
  display: flex;
`;

const VideoContainer = styled.div`
  margin-top: 30px;
  // width: 50%;
  height: 77vh;
  width: 1000px;
  // height: 500px;
  overflow: hidden;
  display: flex;
  justify-content: left;
`;

const StreamContainerWrapper = styled.div`
  display: flex;
  flex-direction:row;
  // place-items: center;
  ${(props) =>
    props.primary
      ? `
    grid-template-columns: repeat(1, 1fr);
    `
      : `
    grid-template-columns: repeat(1, 1fr);
    `}
  grid-gap: 0px;
  height: 100%;
  padding: 5px;
  @media screen and (max-width: 1100px) {
    background-color: #D0EDEF;
  }
`;

const StreamContainer = styled.div`
  width: 100%;
  position: relative;
  border-radius: 5px;
  // min-height: 34vh;
  height: 700px;
  width: 1000px;
  overflow: hidden;
  box-sizing: border-box;
`;

const Bottom = styled.div`
  height: 13vh;
  display: flex;
  justify-content: center;
  position: relative;
  align-items: center;
`;

const BottomBox = styled.div`
  display: flex;
  height: 100%;
  width: 20%;
  align-items: center;
  justify-content: space-around;
`;

const Icon = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background-color: #333;
  color: white;
  cursor: pointer;
  transition: 0.1s;
  &:hover {
    background-color: #3c4043;
  }
  ${(props) =>
    props.primary &&
    `
      background-color: red;
      color: white;
      &:hover{
          background-color: red;
      }
    `}
`;

const ChatIconBox = styled.div`
  position: absolute;
  color: white;
  right: 60px;
  top: 50%;
  bottom: 50%;
  cursor: pointer;
`;

class OnlineMeeting_sell extends Component {
  render() {
    const productId = ((window.location.href).split('/').slice(-1))[0]
    this.state.mySessionId = productId
    console.log(productId)
    return (
      <Container>
        <Header>
          <StudyTitle>경매 라이브 방송</StudyTitle>
        </Header>
        <Middle>
          {this.state.session === undefined ? (
            <div
              style={{
                position: "absolute",
                right: "0",
                left: "0",
                width: "300px",
                margin: "auto",
                height: "300px",
              }}
              id="join"
            >
              <div>
                <h1 style={{ color: "white" }}> Join a video session </h1>
                <form
                  style={{ display: "flex", justifyContent: "left" }}
                  className="form-group"
                  onSubmit={this.joinSession}
                >
                  <p className="text-center">
                    <input
                      className="btn btn-lg btn-success"
                      name="commit"
                      type="submit"
                      value="JOIN"
                    />
                  </p>
                </form>
              </div>
            </div>
          ) : null}
          <Left>
            <VideoContainer>
              {this.state.session !== undefined ? (
                <StreamContainerWrapper
                  primary={this.state.isChat}
                  ref={this.userRef}
                >
                  {this.state.publisher !== undefined ? (
                    <StreamContainer key={this.state.publisher.stream.streamId}>
                      <UserVideoComponent
                        streamManager={this.state.publisher}
                      />
                    </StreamContainer>
                  ) : null}
                  {/* {this.state.subscribers.map((sub, i) => (
                    <StreamContainer key={sub.stream.streamId}>
                      <UserVideoComponent streamManager={sub} />
                    </StreamContainer>
                  ))} */}
                </StreamContainerWrapper>
              ) : null}
            </VideoContainer>
          </Left>
          <Right primary={this.state.isChat}>
            <LiveChat
              id={this.id}
              sellerId={this.sellerId}
              productId={productId}
            />
          </Right>
        </Middle>
        <Bottom>
          <BottomBox>
            <Icon
              primary={!this.state.isCamera}
              onClick={() => this.handleToggle("camera")}
            >
              {this.state.isCamera ? (
                <VideocamOutlinedIcon />
              ) : (
                <VideocamOffOutlinedIcon />
              )}
            </Icon>

            <Icon
              primary={!this.state.isMike}
              onClick={() => this.handleToggle("mike")}
            >
              {this.state.isMike ? <MicOutlinedIcon /> : <MicOffIcon />}
            </Icon>

            <Icon
              primary={!this.state.isSpeaker}
              onClick={() => this.handleToggle("speaker")}
            >
              {this.state.isSpeaker ? <HeadsetIcon /> : <HeadsetOffIcon />}
            </Icon>
            {/* <Icon primary onClick={this.leaveSession ; navigate(`${/chat_onetoone/productId}`)}> */}
              <Icon primary onClick={this.leaveSession}>
              <CallEndIcon />
            </Icon>
          </BottomBox>
          {/* <ChatIconBox
            onClick={() => this.setState({ isChat: !this.state.isChat })}
          >
            <ChatIcon />
          </ChatIconBox> */}
        </Bottom>
      </Container>
    );
  }

  constructor(props) {
    super(props);
    this.userRef = React.createRef();

    this.id = this.props.location.state.id
    this.sellerId = this.props.location.state.sellerId

    this.state = {
      mySessionId: undefined,
      myUserName: "Participant" + Math.floor(Math.random() * 100),
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined, // 로컬 웹캠 스트림
      subscribers: [], // 다른 사용자의 활성 스트림
      isMike: true,
      isCamera: true,
      isSpeaker: true,
      isChat: true,
    };

    this.joinSession = this.joinSession.bind(this);
    this.leaveSession = this.leaveSession.bind(this);
    this.handleMainVideoStream = this.handleMainVideoStream.bind(this);
    this.onbeforeunload = this.onbeforeunload.bind(this);
    this.handleToggle = this.handleToggle.bind(this);
  }

  componentDidMount() {
    // this.leaveSession();
    this.joinSession();
    window.addEventListener("beforeunload", this.onbeforeunload);
    // 스터디방에서 화상회의 입장 -> props로 roomId로 받으면 세션id 업뎃 user 정보 전역변수 가져옴 -> 상태값 업뎃
  }

  componentWillUnmount() {
    window.removeEventListener("beforeunload", this.onbeforeunload);
  }

  onbeforeunload(e) {
    this.leaveSession();
  }

  // 화상회의 나갈때
  leaveSession() {
    const mySession = this.state.session;

    if (mySession) {
      mySession.disconnect();
    }

    this.OV = null;
    this.setState({
      session: undefined,
      subscribers: [],
      mySessionId: undefined,
      myUserName: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
    });
  }

  deleteSubscriber(streamManager) {
    let subscribers = this.state.subscribers;
    let index = subscribers.indexOf(streamManager, 0);
    if (index > -1) {
      subscribers.splice(index, 1);
      this.setState({ subscribers: subscribers });
    }
  }

  handleMainVideoStream(stream) {
    if (this.state.mainStreamManager !== stream) {
      this.setState({ mainStreamManager: stream });
    }
  }

  handleToggle(kind) {
    if (this.state.publisher) {
      switch (kind) {
        case "camera":
          this.setState({ isCamera: !this.state.isCamera }, () => {
            console.log(this.state.publisher);
            this.state.publisher.publishVideo(this.state.isCamera);
          });
          break;

        case "speaker":
          this.setState({ isSpeaker: !this.state.isSpeaker }, () => {
            this.state.subscribers.forEach((s) =>
              s.subscribeToAudio(this.state.isSpeaker)
            );
          });
          break;

        case "mike":
          this.setState({ isMike: !this.state.isMike }, () => {
            this.state.publisher.publishAudio(this.state.isMike);
          });
          break;
      }
    }
  }

  joinSession() {
    this.OV = new OpenVidu(); // OpenVidu 객체를 얻음

    this.OV.setAdvancedConfiguration({
      publisherSpeakingEventsOptions: {
        interval: 50,
        threshold: -75,
      },
    });

    this.setState(
      {
        session: this.OV.initSession(),
      },
      () => {
        let mySession = this.state.session;

        // Session 객체가 각각 새로운 stream에 대해 구독 후, subscribers 상태값 업뎃
        mySession.on("streamCreated", (e) => {
          // OpenVidu -> Session -> 102번째 줄 확인 UserVideoComponent를 사용하기 때문에 2번째 인자로 HTML
          // 요소 삽입X
          let subscriber = mySession.subscribe(e.stream, undefined);
          var subscribers = this.state.subscribers;
          subscribers.push(subscriber);

          this.setState({ subscribers });

          console.log(subscribers);
        });

        // 사용자가 화상회의를 떠나면 Session 객체에서 소멸된 stream을 받아와 subscribers 상태값 업뎃
        mySession.on("streamDestroyed", (e) => {
          this.deleteSubscriber(e.stream.streamManager);
        });

        // 서버 측에서 비동기식 오류 발생 시 Session 객체에 의해 트리거되는 이벤트
        mySession.on("exception", (exception) => {
          console.warn(exception);
        });

        // 발언자 감지
        mySession.on("publisherStartSpeaking", (event) => {
          for (let i = 0; i < this.userRef.current.children.length; i++) {
            if (
              JSON.parse(event.connection.data).clientData ===
              this.userRef.current.children[i].innerText
            ) {
              this.userRef.current.children[i].style.borderStyle = "solid";
              this.userRef.current.children[i].style.borderColor = "#1773EA";
            }
          }
          console.log(
            "User " + event.connection.connectionId + " start speaking"
          );
        });

        mySession.on("publisherStopSpeaking", (event) => {
          console.log(
            "User " + event.connection.connectionId + " stop speaking"
          );
          for (let i = 0; i < this.userRef.current.children.length; i++) {
            if (
              JSON.parse(event.connection.data).clientData ===
              this.userRef.current.children[i].innerText
            ) {
              this.userRef.current.children[i].style.borderStyle = "none";
            }
          }
        });

        this.getToken().then((token) => {
          mySession
            .connect(token, {
              clientData: this.state.myUserName,
            })
            .then(() => {
              let publisher = this.OV.initPublisher(undefined, {
                audioSource: undefined,
                videoSource: undefined, // 웹캠 기본 값으로
                publishAudio: true,
                publishVideo: true,
                resolution: "1280x960",
                frameRate: 30,
                insertMode: "APPEND",
                mirror: "true",
              });

              mySession.publish(publisher);

              this.setState({ mainStreamManager: publisher, publisher });
            })
            .catch((error) => {
              console.log("세션 연결 오류", error.code, error.message);
            });
        });
      }
    );
  }

  getToken() {
    return this.createSession(this.state.mySessionId).then((sessionId) =>
      this.createToken(sessionId)
    );
  }

  createSession(sessionId) {
    return new Promise((resolve, reject) => {
      let data = JSON.stringify({ customSessionId: sessionId });

      axios
        .post(OPENVIDU_SERVER_URL + "/api/sessions", data, {
          headers: {
            Authorization: `Basic ${btoa(
              `OPENVIDUAPP:${OPENVIDU_SERVER_SECRET}`
            )}`,
            "Content-Type": "application/json",
            "Authentication": "eyJyZWdEYXRlIjoxNjc1MjM2MjU5OTczLCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInByb2ZpbGUiOiIiLCJsb2NhbHRpb24iOiIiLCJpZCI6NzAwMywidXNlcm5hbWUiOiJuYXZlcl9rd0FqYXMtU0JqMlhlaHdZMG1LVnViWFNxbjNISGZ0WHdoZG5NcGdJRERjIiwiZXhwIjoxNjc1MjM4MDU5fQ.j0Q2aPosXqsX9PmyKSKdVtr9-4eUcq895TDgk6Lyq7E",
          },
        })
        .then((res) => {
          resolve(res.data);
          console.log(res)
        })
        .catch((res) => {
          let error = Object.assign({}, res);
          console.log(res.data.id)
          if (error?.response?.status === 409) {
            resolve(sessionId);
          } else if (
            window.confirm(
              'No connection to OpenVidu Server. This may be a certificate error at "' +
                OPENVIDU_SERVER_URL +
                '"\n\nClick OK to navigate and accept it. If no certifica' +
                "te warning is shown, then check that your OpenVidu Server is up and running at" +
                ' "' +
                OPENVIDU_SERVER_URL +
                '"'
            )
          ) {
            window.location.assign(OPENVIDU_SERVER_URL + "/accept-certificate");
          }
        });
    });
  }

  createToken(sessionId) {
    return new Promise((resolve, reject) => {
      let data = {};

      axios
        .post(
          `${OPENVIDU_SERVER_URL}/api/sessions/${sessionId}/connections`,
          data,
          {
            headers: {
              Authorization: `Basic ${btoa(
                `OPENVIDUAPP:${OPENVIDU_SERVER_SECRET}`
              )}`,
              "Content-Type": "application/json",
              "Authentication": "eyJyZWdEYXRlIjoxNjc1MjM2MjU5OTczLCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiVVNFUiIsInByb2ZpbGUiOiIiLCJsb2NhbHRpb24iOiIiLCJpZCI6NzAwMywidXNlcm5hbWUiOiJuYXZlcl9rd0FqYXMtU0JqMlhlaHdZMG1LVnViWFNxbjNISGZ0WHdoZG5NcGdJRERjIiwiZXhwIjoxNjc1MjM4MDU5fQ.j0Q2aPosXqsX9PmyKSKdVtr9-4eUcq895TDgk6Lyq7E",
            },
          }
        )
        .then((res) => {
          console.log("res")
          console.log((res.data.substring(res.data.lastIndexOf('&') + 1)).substring(6))
          resolve(res.data);
        })
        .catch((error) => reject(error));
    });
  }
}

export default WithRouter(OnlineMeeting_sell);