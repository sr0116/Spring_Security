import {useEffect, useState} from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import {decode} from "base-64";


// JWT 파싱 함수
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
// 이게 리스트
export default function List() {
  const [list, setList] = useState([]);
  const [page, setPage] = useState(1); // 1페이지 부터
  const [size] = useState(5); // 5개씩
  const [pageInfo, setPageInfo] = useState({}); //페이징 정보
  const [keyword, setKeyword] = useState(""); // 검색 타입
  const [type, setType] = useState("ALL");
  const navigate = useNavigate();

  const token = localStorage.getItem("token");
// 리스트 불러오기
  useEffect(() => {
    const payloadObj = parseJwt(token);
    const email = payloadObj.sub;
    // 토큰 확인
    if (!token) return navigate("/login");
    if (!payloadObj) return navigate("/login");
    axios
      .get(`http://localhost:8080/notes/`, {
        headers: {Authorization: `Bearer ${token}`}, // 권한 확인
        params: { page, size, sort: "regDate", direction: "desc" } // 페이지 처리까지
      })
      .then((res) => {
        console.log(res.data);
        setList(res.data.dtoList);
        setPageInfo(res.data)
      })
      .catch((err) => console.error("err:", err));
  }, [page, size, token, navigate]);//[token, navigate] useEffect 안에서 token, navigate 사용해서

// 삭제
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
  // 검색
  const handleSearch = () => {
    if (!keyword.trim()) {
      alert("검색어를 입력하세요");
      return;
    }
    axios.post(`http://localhost:8080/notes/search`,
      { keyword, type }, // body
      { headers: { Authorization: `Bearer ${token}` } }
    )
      .then((res) => {
        setList(res.data);  // 검색 결과를 리스트에 넣기
      })
      .catch(err => console.error("검색 오류:", err));
  };
  return (
    <div>
      <div style={{ marginBottom: "15px" }}>
        <input
          type="text"
          placeholder="검색어 입력"
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
        />
        <select value={type} onChange={(e) => setType(e.target.value)}>
          <option value="ALL">전체</option>
          <option value="TITLE">제목</option>
          <option value="CONTENT">내용</option>
        </select>
        <button onClick={handleSearch}>검색</button>
      </div>
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
              <td>
                <button  onClick={(e) => {e.stopPropagation();handleRemove(dto.num);}}>삭제</button>
              </td>
            </tr>))) : (<tr><td colSpan="3" className="text-center">
              데이터가 없습니다.
            </td></tr>)}</tbody><tfoot>
        <tr>
          <td colSpan="3" className="text-center">
            <button onClick={() => navigate("/register")}>글작성</button>
          </td>
        </tr>
        </tfoot>
      </table>
      <div style={{ textAlign: "center", marginTop: "10px" }}>
        <button onClick={() => setPage(page - 1)} disabled={page <= 1}>
          이전
        </button>
        <span style={{ margin: "0 10px" }}>
          {page} / {pageInfo.totalPages || 1}
        </span>
        <button
          onClick={() => setPage(page + 1)}
          disabled={page >= (pageInfo.totalPages || 1)}
        >
          다음
        </button>
      </div>

    </div>
  );
}
