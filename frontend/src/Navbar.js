import React, { Component } from "react";
import styled, { keyframes } from "styled-components";
import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Offcanvas from 'react-bootstrap/Offcanvas';
import Category from "./Category";

// import Example from './Category'


// const Left = styled.div`
//   position: relative;
//   padding: 0 20px;
//   display: flex;
//   align-items: center;
//   transition: 0.5s;
//   ${(props) =>
//     props.primary ? `left:0; flex:1;` : `right:calc(-100vw/3); flex:0;`}
// `;

// const Cate = styled.div`
//   width: 100%;
//   height: 93%;
//   border-radius: 5px;
//   background-color: white;
//   display: flex;
// `;

// const animation = keyframes`
//   0% {
//     transform:rotate(0deg);
//     border-radius:0px;
//   }
//   50% {
//     border-radius:100px;
//   }
//   100%{
//     transform:rotate(360deg);
//     border-radius:0px;
//   }
// `;

// const Box = styled.div`
//   height: 200px;
//   width: 200px;
//   background-color: tomato;
//   animation:${animation} 1s linear infinite; //1초동안 선형 무한 속성값주기
// `;
const options = [
  {
    name: 'Enable backdrop (default)',
    scroll: false,
    backdrop: true,
  },
];

function OffCanvasExample({ name, ...props }) {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const toggleShow = () => setShow((s) => !s);

  return (
    <>
    <div>
      <Button variant="primary" onClick={toggleShow} className="me-2">
        <img className="category_btn1" src="category_btn.png" alt="category_btn1"/>
      </Button>
      <Offcanvas className="cate_zone" show={show} onHide={handleClose} {...props}>
        {/* <Offcanvas.Header closeButton> */}
          <Offcanvas.Title><h2>지금 뭐해요??</h2></Offcanvas.Title>
        {/* </Offcanvas.Header> */}
        <hr />
        <Category />
      </Offcanvas>
    </div>
    </> 
  );
}

function Example() {
  return (
    <>
      {options.map((props, idx) => (
        <OffCanvasExample key={idx} {...props} />
      ))}
    </>
  );
}





function Navbar() {
  return (
    <div >
      <div className="navbar_up">
        <a href="https://www.naver.com">
            <img className="App-logo" src="logos/App_logo.png" alt="App_logo" />
            <span>앱 다운로드</span>
        </a>
        <a href="https://www.google.com">고객센터</a>
      </div>
      {/* <hr className="nav_line1"/> */}
      <br />
      <div className="navbar_down">
        <a href="https://www.daum.net" className="W_logo_box"><img className="W_logo" src="logos/W_logo.png" alt="W_logo"/></a>
        <span className="W_logo_box"><input className="nav_input" type="text" placeholder=" 검색해보세요" /></span>
        <span>
          <div class="nav_btn_box">
            <a href="https://www.kakaocorp.com/"><img className="chat_icon" src="chat_icon.png" alt="chat_icon" /></a>
            <a href="https://www.coupang.com/"><img className="myprofile_icon" src="myprofile_icon.png" alt="myprofile_icon" /></a>
            <a href="https://www.daangn.com/"><img className="logout_icon" src="logout_icon.png" alt="logout_icon" /></a>
          </div>
        </span>
          {/* 아래는 로그아웃일 경우의 버튼 -> 향후 상태변화 구현 시 사용 */}
          {/* <br />
          <span>
            <div class="nav_btn_box">
              <a href="https://www.kakaocorp.com/"><img className="login_icon" src="login_icon.png" alt="login_icon" /></a>
              <a href="https://www.coupang.com/"><img className="signup_icon" src="signup_icon.png" alt="signup_icon" /></a>
            </div>
          </span> */}
      </div>
      <div>
        <span>
          <Example />
      
          {/* // onClick={() => this.setState({ isCategory: !this.state.isCategory })} */}
          {/* // />
          // <Left primary={this.state.isCategory}>
          //   <Cate>
          //   <Category />
          //   </Cate>
          // </Left> */}
          <hr className="nav_line2"/>
        </span>
      </div>
    </div>
  )

  
  // function Example() {
  //   const [show, setShow] = useState(false);
  
  //   const handleClose = () => setShow(false);
  //   const handleShow = () => setShow(true);
  
  //   return (
  //     <>
  //       <Button className="category_btn2" variant="primary" onClick={handleShow}>
  //         <img className="category_btn1" src="category_btn.png" alt="category_btn1"/>
  //       </Button>
  
  //       <Offcanvas show={show} onHide={handleClose}>
  //         <Offcanvas.Header closeButton>
  //           <Offcanvas.Title>Category</Offcanvas.Title>
  //         </Offcanvas.Header>
  //         <Offcanvas.Body>
  //           <div>
  //             <a href="">카테고리1</a>
  //             <br />
  //             <br />
  //             <a href="">카테고리2</a>
  //             <br />
  //             <br />
  //             <a href="">카테고리3</a>
  //             <br />
  //             <br />
  //             <a href="">카테고리4</a>
  //             <br />
  //             <br />
  //             <a href="">카테고리5</a>
  //             <br />
  //             <br />
  //             <a href="">카테고리6</a>
  //             <br />
  //             <br />
  //             <a href="">카테고리7</a>
  //             <br />
  //             <br />
  //             <a href="">카테고리8</a>
  //             <br />
  //             <br />
  //             <a href="">카테고리9</a>
  //             <br />
  //             <br />
  //             <a href="">카테고리10</a>
  //           </div>
  //         </Offcanvas.Body>
  //       </Offcanvas>
  //     </>
  //   );
  // }

}

export default Navbar