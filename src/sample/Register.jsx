import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { parseJwt } from "./List";
import { notesApi } from "../api/notesApi";

export default function Register() {
  const [noteDTO, setNoteDTO] = useState({ title: "", content: "", writerEmail: "" });
  const token = localStorage.getItem("token");
  const navigate = useNavigate();

  useEffect(() => {
    if (token) {
      const payloadObj = parseJwt(token);
      setNoteDTO((prev) => ({ ...prev, writerEmail: payloadObj.sub }));
    }
  }, [token]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNoteDTO((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    notesApi.create(noteDTO, token)
      .then((res) => {
        alert(`${res.data.num}번 글 등록 성공!`);
        navigate("/");
      })
      .catch(() => alert("등록 실패"));
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="text" name="title" placeholder="제목" value={noteDTO.title} onChange={handleChange} />
      <input type="text" name="content" placeholder="내용" value={noteDTO.content} onChange={handleChange} />
      <input type="email" name="writerEmail" value={noteDTO.writerEmail} readOnly />
      <button type="submit">글 등록</button>
    </form>
  );
}
