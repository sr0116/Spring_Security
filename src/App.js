import logo from './logo.svg';
import './App.css';
import {BrowserRouter, Route, Router, Routes} from "react-router-dom";
import Login from "./cors/Login";
import Read from "./cors/Read";
import List from "./cors/List";
import Modify from "./cors/Modify";
import Register from "./cors/Register";


function App() {
  return (
  <div>
    <BrowserRouter>
      <Routes>
        <Route index element={<Login />} />
        <Route path="read/:num" element={<Read />} />
        <Route path="list" element={<List />} />
        <Route path="modify/:num" element={<Modify />} />
        <Route path="register" element={<Register/>} />
        </Routes>
    </BrowserRouter>

  </div>
  );
}

export default App;
