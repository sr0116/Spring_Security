import { useState } from "react";
import axios from "axios";

export default function Search({ onResult }) {
  const [keyword, setKeyword] = useState("");
  const [type, setType] = useState("ALL");

  const handleSearch = async () => {
    try {
      const token = localStorage.getItem("token");

      const res = await axios.post(
        "http://localhost:8080/notes/search",
        { keyword, type },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      onResult(res.data); // 부모에게 검색 결과 전달
    } catch (err) {
      console.error("검색 오류:", err);
      alert("검색 실패");
    }
  };

  return (
    <div>
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
  );
}
