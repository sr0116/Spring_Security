import axios from "axios";
import {useState, useEffect} from "react";

export default function Cors (props) {
 const [data, setData] = useState();


  const handleClick = () => {
    axios.get("http://localhost:8080/notes/11")
      .then((res) => {
        console.log("응답 데이터:", res.data); // 확인용
        setData(res.data); // 상태에 저장
      })
      .catch((err) => {
        console.error("에러 발생:", err);
      });
  };

  return (
    <div>
      <button onClick={handleClick}>CORS Check</button>
      <p>{data}</p>
    </div>
  )
}