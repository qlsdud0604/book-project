import React from 'react';
import { Container } from 'react-bootstrap';
import { Route } from 'react-router';
import Header from './components/Header';
import Detail from './pages/book/Detail';
import Home from './pages/book/Home';
import SaveForm from './pages/book/SaveForm';
import UpdateForm from './pages/book/UpdateForm';
import JoinForm from './pages/user/JoinForm';
import Loginform from './pages/user/Loginform';

function App() {
  return (
    <div>
      <Header />
      <br />
      <Container>
        {/* 등록된 책의 전체 리스트 조회 */}
        <Route path="/" exact={true} component={Home} />

        {/* 책 정보 등록 */}
        <Route path="/saveForm" exact={true} component={SaveForm} />

        {/* 특정 책의 상세 정보 조회 */}
        <Route path="/book/:id" exact={true} component={Detail} />

        {/* 책 정보 수정 */}
        <Route path="/updateForm/:id" exact={true} component={UpdateForm} />

        {/* 로그인 */}
        <Route path="/loginform" exact={true} component={Loginform} />

        {/* 회원가입 */}
        <Route path="/joinForm" exact={true} component={JoinForm} />
      </Container>
    </div>
  );
}

export default App;
