import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { notesApi } from "../api/notesApi";

export default function Modify() {
  const [noteDTO, setNoteDTO] = useState({ num: "", title: "", content: "" });
  const { num } = useParams();
  const token = localStorage.getItem("token");
  const navigate = useNavigate();

  useEffect(() => {
    notesApi.getOne(num, token)
      .then((res) => setNoteDTO(res.data))
      .catch(() => alert("데이터 불러오기 실패"));
  }, [num, token]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNoteDTO((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    notesApi.update(noteDTO, token)
      .then(() => {
        alert("수정 성공");
        navigate("/");
      })
      .catch(() => alert("수정 실패"));
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="text" name="title" value={noteDTO.title} onChange={handleChange} />
      <input type="text" name="content" value={noteDTO.content} onChange={handleChange} />
      <button type="submit">수정 등록</button>
    </form>
  );
}
