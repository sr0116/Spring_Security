import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import Cors from "./cors/Cors";
import Login from "./cors/Login";
import {AuthProvider} from "./cors/Authcontext";
import App2 from "./sample/App2";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  // AuthProvider 대신에  옆에걸로 교체 <AuthContext.Provider value={{token, login, logout}}>  {children} </AuthContext.Provider>
  <AuthProvider> {/*토큰 관련  컴포넌트로 감싸주기*/}
    <App />
  </AuthProvider>

);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
