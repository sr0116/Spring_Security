package com.imchobo.club.service;

import com.imchobo.club.dto.NoteDto;
import com.imchobo.club.entity.ClubMember;
import com.imchobo.club.entity.Note;

import java.util.List;

public interface NoteService {
  Long register(NoteDto noteDto);

  NoteDto get (Long num);

  void modify(NoteDto noteDto);
  void remove(Long num);

  List<NoteDto> getAllWithWriter(String writerEmail);

  default  Note dtoToEntity(NoteDto noteDto){
    Note note = Note.builder()
      .num(noteDto.getNum())
      .title(noteDto.getTitle())
      .content(noteDto.getContent())
      .writer(ClubMember.builder()
        .email(noteDto.getWriterEmail()).build())
      .build();
    return note;
  }

  // Entity -> DTO 변환
  // Entity(Note) 객체를 DTO(NoteDTO)로 변환하는 기본 메서드
  default NoteDto entityToDto(Note note) {

    // NoteDTO 객체를 빌더 패턴으로 생성
    NoteDto noteDTO = NoteDto.builder()
      .num(note.getNum())                               // 엔티티의 num → DTO num
      .title(note.getTitle())                           // 엔티티의 title → DTO title
      .content(note.getContent())                       // 엔티티의 content → DTO content
      .writerEmail(note.getWriter().getEmail())         // 엔티티의 writer(ClubMember).email → DTO writerEmail
      .regDate(note.getRegDate())                       // 엔티티의 등록일 → DTO regDate
      .modDate(note.getModDate())                       // 엔티티의 수정일 → DTO modDate
      .build();                                         // 최종 NoteDTO 생성

    return noteDTO; // 변환된 DTO 반환
  }

}
