import { BrowserRouter, Routes, Route } from "react-router-dom";
// cors 폴더 컴포넌트 import
import Login from "../cors/Login";
import List from "../cors/List";
import Read from "../cors/Read";
import Modify from "../cors/Modify";
import Register from "../cors/Register";
import Logout from "../cors/Logout";
function App2() {
  return (
    <BrowserRouter>
      {/*  헤더 (로그인/로그아웃 버튼) */}
      <Logout />

      {/*  라우터 설정 */}
      <Routes>
        <Route path="/login" element={<Login />} />      {/* 로그인 */}
        <Route path="/" element={<List />} />           {/* 글 목록 */}
        <Route path="/read/:num" element={<Read />} />  {/* 글 상세 */}
        <Route path="/modify/:num" element={<Modify />} /> {/* 글 수정 */}
        <Route path="/register" element={<Register />} /> {/* 글 등록 */}
      </Routes>
    </BrowserRouter>
  );
}

export default App2;
