import {useState} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {useAuth} from "./Authcontext";

export default function Login() {
  // id / pw를 하나의 객체로 관리
  const [loginInfo, setLoginInfo] = useState({
    email: "",
    pw: ""
  });
  const navigate = useNavigate();
  // 토큰 저장 및 삭제 (토큰이 필요 없으면 지워도 됨)
  const {token, login, logout } = useAuth();

  // input 값 변경 처리
  const handleChange = (e) => {
    const {name, value} = e.target;
    setLoginInfo((prev) => ({
      ...prev,
      [name]: value
    }));
  };

  // Content-Type 은
  // text/ html
  // text.plain
  // application/json
  // application/xml
  // application/x-www-form-urlencoded
  // multipart/form-data
  //   (`http://localhost:8080/api/login?email=${loginInfo.email}&pw=${loginInfo.pw}` )
  const handleSubmit = e => {
    e.preventDefault();

    //axios.get(`http://localhost:8080/api/login?email=${loginInfo.email}&pw=${loginInfo.pw}`, loginInfo )
    axios.post('http://localhost:8080/api/login', loginInfo, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
      .then(res => {
        alert("로그인 성공")
        console.log("받은 데이터", res.data);
        login(res.data);
        // sessionStorage.setItem("token", res.data);
        navigate(`/`); // 성공 시에
      })
      .catch((err) => {
        console.error("로그인 실패:", err);
        alert("로그인 실패");
      })
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <p>
          <input type="text" name="email" placeholder="이메일" value={loginInfo.email} onChange={handleChange}/></p>
        <p>
          <input type="password" name="pw" placeholder="비밀번호" value={loginInfo.pw} onChange={handleChange}/>
        </p>
        <div>
        {/*  <button type="submit">로그인</button>*/}
        {/* < button type="submit" onClick={() => {*/}
        {/*logout();*/}
        {/*  }}> 로그아웃</button>*/}
          {!token ? (
          <button type="submit">로그인</button>) :
            ( <button type="submit" onClick={() => {
            logout();
          }}>
            로그아웃</button>)
          }
        </div>
      </form>
    </div>
  );
}
