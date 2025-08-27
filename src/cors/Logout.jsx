import {useAuth} from "./Authcontext";
import {useNavigate, useLocation} from "react-router-dom";
import {useState, useEffect} from "react";
import Login from "./Login";

export default function Logout () {
  const {token, login, logout } = useAuth();
  const navigate = useNavigate();
  const [list, setList] = useState([]);
  const location = useLocation();
  // 로그인 정보 없으면
  useEffect(() => {
    // 새로고침 시 토큰 없으면 로그인 페이지로 강제 이동
    if (!token) {
      navigate("/login");
    }
  }, [token, navigate]);
  // 함수로 뺌
  const handleLogout = () => {
    logout();
    setList([]);
    navigate("/login");
  }
// 로그인 페이지에서는 안뜨게
  // 헤더라고 보면됨
  if (location.pathname === "/login") {
    return null;
  }

  return (
    <div>
      {!token ? (
         <Login />):
        ( <button type="submit" onClick={handleLogout}>
          로그아웃</button>)
      }
    </div>
  );
}