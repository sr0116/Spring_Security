// src/api/notesApi.js
import axios from "axios";

const BASE_URL = "http://localhost:8080";

// 공통 헤더 처리
function authHeader(token) {
  return { Authorization: `Bearer ${token}` };
}

export const notesApi = {
  // 로그인
  login: (loginInfo) =>
    axios.post(`${BASE_URL}/api/login`, loginInfo, {
      headers: { "Content-Type": "application/json" },
    }),

  // 글 목록 조회
  getList: (email, token) =>
    axios.get(`${BASE_URL}/notes/all?email=${email}`, {
      headers: authHeader(token),
    }),

  // 글 단건 조회
  getOne: (num, token) =>
    axios.get(`${BASE_URL}/notes/${num}`, {
      headers: authHeader(token),
    }),

  // 글 등록
  create: (noteDTO, token) =>
    axios.post(`${BASE_URL}/notes/`, noteDTO, {
      headers: { ...authHeader(token), "Content-Type": "application/json" },
    }),

  // 글 수정
  update: (noteDTO, token) =>
    axios.put(`${BASE_URL}/notes/${noteDTO.num}`, noteDTO, {
      headers: { ...authHeader(token), "Content-Type": "application/json" },
    }),

  // 글 삭제
  remove: (num, token) =>
    axios.delete(`${BASE_URL}/notes/${num}`, {
      headers: authHeader(token),
    }),
};
