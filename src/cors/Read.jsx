import {useState, useEffect} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {useParams} from "react-router-dom";


export default function Read (){

  const [noteDTO, setNoteDTO] = useState({
    num:"", title:"", content:""
  });
  const {num} =useParams();

  const navigate = useNavigate();

  useEffect(() => {
    // const token = sessionStorage.getItem("token"); // 세션이면 세션 로컬이면 로켜\ㅓㄹ로 맞춰줘야 함
    const token = localStorage.getItem("token");
    axios.get(`http://localhost:8080/notes/${num}`, {
      headers: {
        'Authorization':`Bearer ${token}`,
      }
    })
      .then(res => {
        console.log(res.data);
        setNoteDTO(res.data);
      })
      .catch(err => {
        console.log("err", err);
      })
    }, []);

  const handleRemove = () => {
    const token = localStorage.getItem("token");
// 권한이 필요해서 무조건 토큰 정보 필요
    axios.delete(`http://localhost:8080/notes/${num}` , {
      headers: { Authorization: `Bearer ${token}` }
    })
      .then(res => {
        console.log(res.data);
        setNoteDTO(res.data);
        alert("삭제 성공");
        navigate("/");
      })
      .catch(err => {
        console.error("삭제 실패", err);
        alert("삭제 실패");
      });
  }
  return (
    <div>
       <p>num: {noteDTO.num}</p>
      <p>title: {noteDTO.title}</p>
      <p>content: {noteDTO.content}</p>
      <button onClick={() => navigate(`/modify/${num}`)}>수정</button>
      <button onClick={handleRemove}>삭제</button>
      <button onClick={() => navigate(`/`)}>메인</button>

    </div>
  );
}