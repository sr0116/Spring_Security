import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import {decode} from "base-64";
import Login from "./Login";

export function parseJwt(token) {
  try {
    const base64Url = token.split(".")[1];
    const jsonPayload = decode(base64Url);
    return JSON.parse(jsonPayload);
  } catch (e) {
    console.error("JWT 파싱 오류:", e);
    return null;
  }
}

export default function List() {
  const [list, setList] = useState([]);
  const navigate = useNavigate();
  // let email = ";"
  // let token = ";"
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) {
      navigate("/");
      return;
    }
    const payloadObj = parseJwt(token);
    console.log(payloadObj);
    if (!payloadObj) {
      navigate("/");
      return;
    }
    const email = payloadObj.sub;
    console.log(email);
    axios
      .get(`http://localhost:8080/notes/all?email=${email}`, {
        headers: {Authorization: `Bearer ${token}`}, // 권한 확인
      })
      .then((res) => {
        console.log(res.data);
        setList(res.data);
      })
      .catch((err) => console.error("err:", err));
  }, []);

  const handleRemove = (num)=> {
    axios.delete(`http://localhost:8080/notes/${num}`, {
      headers: {Authorization: `Bearer ${token}`},
    })
      .then(res => {
        alert("글 삭제")
        // 삭제 조건
        setList(prev => prev.filter(item => item.num !== num));
      })
      .catch(err => {
        console.log(err);
      })
  }

  return (
    <div>
      <table className="table table-hover">
        <thead>
        <tr>
          <th>글번호</th>
          <th>제목</th>
          <th>작성자</th>
        </tr>
        </thead>
        <tbody>
        {Array.isArray(list) && list.length > 0 ? (
          list.map((dto) => (
            <tr
              key={dto.num}
              onClick={() => navigate(`/read/${dto.num}`)}
              style={{cursor: "pointer"}}
            >
              <td>{dto.num}</td>
              <td>{dto.title}</td>
              <td>{dto.writerEmail}</td>
              <td><button  onClick={(e) => {
                e.stopPropagation();
                handleRemove(dto.num);
              }}>삭제</button></td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="3" className="text-center">
              데이터가 없습니다.
            </td>
          </tr>
        )}
        </tbody>
        <tfoot>
        <tr>
          <td colSpan="3" className="text-center">
            <button onClick={() => navigate("/register")}>글작성</button>
          </td>
        </tr>
        </tfoot>
      </table>

    </div>
  );
}
