// context 생성
// Context.provider 에서 사용하는 변수, 함수 표현
// 하위 컴포넌트에서 쉽세 사용할 수 있도록 커스텀 hook 구현

import {createContext, useContext, useState} from "react";

// context 생성
const AuthContext = createContext();

// 로그인 했을 때 토큰 저장, 로그아웃, 토큰 삭제, 토큰 저장 변수
// 다양하게 함수 정의 가능
// function AuthProvider(props) {
//   const {children} = props;
// }
// const AuthProvider2 =  props => {
//   const {children} = props;
// }
// const AuthProvider3 = ({children}) => {}

export function AuthProvider(props) {
  const {children} = props;
  // 로그인 풀림
  // const [token, setToken] = useState(null);
  // const login = newToken => {
  //   setToken(newToken);
  //   localStorage.setItem('token', newToken);
  // }
  //초기값을 localStorage에서 가져오기
  const [token, setToken] = useState(() => localStorage.getItem("token"));

  const login = (newToken) => {
    setToken(newToken);
    localStorage.setItem("token", newToken);
  };

//  로그인 로그아웃 두개 한 번에
  const logout = () => {
    setToken(null);
    localStorage.removeItem('token');
  }

  return (
    <AuthContext.Provider value={{token, login, logout}}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = ()=> useContext(AuthContext);

// 사용할 때는
// <AuthContext.Provider value={token, login, logout}}
// <App />