import logo from './logo.svg';
import './App.css';
import Main from './Router/Main'
import Register from './Router/Register'
import Edit from './Router/Edit'
import Profile from './Router/Profile'
import Detail from './Router/Detail'
import Search from './Router/Search'
import Live from './Router/Live'
import Chat from './Router/Chat'
import Login from './Login/Login'
import Navbar from './Navbar'
import OnlineMeeting from './OnlineMeeting'
import { BrowserRouter as Router, Routes, Route, Link, useNavigate, Outlet } from 'react-router-dom'


function App() {
  return (
    <div className="App">
      <Navbar />
      <Router>
        <Routes>

          <Route path="/" element={
            <Main />
          } />

          <Route path="/register" element={
            <Register />
          } />
          
          <Route path="/edit" element={
            <Edit />
          } />

          <Route path="/chat" element={
            <Chat />
          } />

          <Route path="/live" element={
            <OnlineMeeting />
          } />

          <Route path="/profile" element={
            <Profile />
          } />

          <Route path="/detail" element={
            <Detail />
          } />

          <Route path="/search" element={
            <Search />
          } />
          
          <Route path="/login" element={
            <Login />
          } />

        </Routes>
      </Router>
    </div>
  );
}

export default App;
