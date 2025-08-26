import {useState, useEffect} from "react";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import {useParams} from "react-router-dom";


export default function Read (){

  const [noteDTO, setNoteDTO] = useState({
    num:"", title:"", content:""
  });
  const {num} =useParams();

  const nevigate = useNavigate();

  useEffect(() => {
    const token = sessionStorage.getItem("token");
    axios.get("http://localhost:8080/notes/11", {
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

  return (
    <div>
       <p>num: {noteDTO.num}</p>
      <p>title: {noteDTO.title}</p>
      <p>content: {noteDTO.content}</p>
      <button onClick={() => nevigate(`/modify/${num}`)}>수정</button>

    </div>
  );
}