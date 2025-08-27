import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { notesApi } from "../api/notesApi";

export default function Read() {
  const [noteDTO, setNoteDTO] = useState({ num: "", title: "", content: "" });
  const { num } = useParams();
  const token = localStorage.getItem("token");
  const navigate = useNavigate();

  useEffect(() => {
    notesApi.getOne(num, token)
      .then((res) => setNoteDTO(res.data))
      .catch(() => alert("불러오기 실패"));
  }, [num, token]);

  const handleRemove = () => {
    notesApi.remove(num, token)
      .then(() => {
        alert("게시글 삭제");
        navigate("/");
      })
      .catch(() => alert("삭제 실패"));
  };

  return (
    <div>
      <p>num: {noteDTO.num}</p>
      <p>title: {noteDTO.title}</p>
      <p>content: {noteDTO.content}</p>
      <button onClick={() => navigate(`/modify/${num}`)}>수정</button>
      <button onClick={handleRemove}>삭제</button>
      <button onClick={() => navigate("/")}>메인</button>
    </div>
  );
}
