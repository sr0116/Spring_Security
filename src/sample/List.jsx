import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { parseJwt } from "./utils"; // JWT 파싱 함수 분리해도 좋음
import { notesApi } from "../api/notesApi";

export default function List() {
  const [list, setList] = useState([]);
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  useEffect(() => {
    if (!token) {
      navigate("/login");
      return;
    }
    const payloadObj = parseJwt(token);
    if (!payloadObj) {
      navigate("/login");
      return;
    }

    const email = payloadObj.sub;
    notesApi.getList(email, token)
      .then((res) => setList(res.data))
      .catch((err) => console.error("err:", err));
  }, [token, navigate]);

  const handleRemove = (num) => {
    notesApi.remove(num, token)
      .then(() => {
        alert("삭제 성공");
        setList((prev) => prev.filter((item) => item.num !== num));
      })
      .catch((err) => console.error("삭제 실패:", err));
  };

  return (
    <div>
      <table className="table table-hover">
        <thead>
        <tr>
          <th>글번호</th>
          <th>제목</th>
          <th>작성자</th>
          <th>관리</th>
        </tr>
        </thead>
        <tbody>
        {list.length > 0 ? (
          list.map((dto) => (
            <tr key={dto.num} style={{ cursor: "pointer" }}>
              <td onClick={() => navigate(`/read/${dto.num}`)}>{dto.num}</td>
              <td onClick={() => navigate(`/read/${dto.num}`)}>{dto.title}</td>
              <td onClick={() => navigate(`/read/${dto.num}`)}>{dto.writerEmail}</td>
              <td>
                <button
                  onClick={(e) => {
                    e.stopPropagation();
                    handleRemove(dto.num);
                  }}
                >
                  삭제
                </button>
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan="4" className="text-center">
              데이터가 없습니다.
            </td>
          </tr>
        )}
        </tbody>
        <tfoot>
        <tr>
          <td colSpan="4" className="text-center">
            <button onClick={() => navigate("/register")}>글작성</button>
          </td>
        </tr>
        </tfoot>
      </table>
    </div>
  );
}
