import {useEffect, useState} from "react";
import axios from "axios";
import {parseJwt} from "./List";

export default function Register () {
  const [noteDTO, setNoteDTO] = useState({
    title:"", content:"", writerEmail:""
  });
  const token = sessionStorage.getItem("token");



  const handleChange = (e) => {
    const { name, value } = e.target;
    setNoteDTO(prev => ({
      ...prev,
      [name]: value
    }));
  };


  useEffect(() => {
    const payloadObj = parseJwt(token);
    const email = payloadObj.sub;
    setNoteDTO(noteDTO => ({
      ...noteDTO, ["writerEmail"]: email // string
    }))
  }, []);
  const handleSubmit = (e) => {
    e.preventDefault();

    axios.post("http://localhost:8080/notes/", noteDTO, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      }
    })
      .then(res => {
        console.log("등록 성공", res.data);
        alert(`${res.data.num}번 글 등록 성공!`);
      })
      .catch(err => {
        console.log("등록 실패", err);
        alert("등록 실패");
      });
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input type="text" onChange={handleChange} name="title" value={noteDTO.title} placeholder="제목"/>
        <input type="text" onChange={handleChange} name="content" value={noteDTO.content} placeholder="내용"/>
        <input type="email" readOnly  name="writerEmail" value={noteDTO.writerEmail} placeholder="작성자 이메일"/>
        <button type="submit">글 등록</button>
      </form>
    </div>
  );
}
