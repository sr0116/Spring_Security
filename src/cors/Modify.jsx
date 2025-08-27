import {useState, useEffect} from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";

export default function Modify () {

  const [noteDTO, setNoteDTO] = useState({
    num:"", title:"", content:""
  });
  const { num } = useParams();

  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem("token");

    // 기존 데이터 불러오기 (예: 11번 note)
    axios.get(`http://localhost:8080/notes/${num}`, {
      headers: {
        'Authorization': `Bearer ${token}`,
      }
    })
      .then(res => {
        console.log(res.data);
        setNoteDTO(res.data);
      })
      .catch(err => {
        console.log("err", err);
      });
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setNoteDTO(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    // const token = sessionStorage.getItem("token");

    console.log("PUT URL:", `http://localhost:8080/notes/${noteDTO.num}`);
    console.log("DTO:", noteDTO);
    console.log("TOKEN:", token);

    axios.put(`http://localhost:8080/notes/${noteDTO.num}`, noteDTO, {
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'

      }
    })
      .then(res => {
        console.log("수정 성공", res.data);
        navigate("/");
      })
      .catch(err => {

        console.log("수정 실패", err);
      });
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <input type="text" onChange={handleChange} name="title" value={noteDTO.title} placeholder="제목"/>
        <input type="text" onChange={handleChange} name="content" value={noteDTO.content} placeholder="내용"/>
        <button type="submit">수정 등록</button>
      </form>
    </div>
  );
}
