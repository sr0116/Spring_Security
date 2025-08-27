import {useAuth} from "./Authcontext";
import {useNavigate, useLocation} from "react-router-dom";
import {useState} from "react";

export default function Logout () {
  const {token, login, logout } = useAuth();
  const navigate = useNavigate();
  const [list, setList] = useState([]);

  // 함수로 뺌
  const handleLogout = () => {
    logout();
    setList([]);
    navigate("/login");
  }


  return (
    <div>
      {!token ? (
          <button type="submit" onClick={() => {
            login();
            navigate("/login");
          }}> 로그인</button> ):
        ( <button type="submit" onClick={handleLogout}>
          로그아웃</button>)
      }
    </div>
  );
}