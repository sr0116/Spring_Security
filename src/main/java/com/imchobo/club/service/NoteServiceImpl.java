package com.imchobo.club.service;

import com.imchobo.club.dto.NoteDto;
import com.imchobo.club.dto.NoteSearchDTO;
import com.imchobo.club.dto.PageResponseDTO;
import com.imchobo.club.entity.Note;
import com.imchobo.club.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

  private final NoteRepository noteRepository;

  @Override
  public Long register(NoteDto noteDto) {
    Note note = dtoToEntity(noteDto);

    log.info("==================================");
    log.info(note);
    noteRepository.save(note);
    return note.getNum();
  }

  @Override
  public NoteDto get(Long num) {
//    Optional<Note> result = noteRepository.getWithWriter(num);
//    if (result.isPresent()) {
//      return entityToDto(result.get());
//    }
//    return null;
    // 위에거 대신에 이렇게도 사용 가능
//    Optional 에 값이 있으면 entityToDto(note) 실행해서 NoteDto 로 변환.
    return noteRepository.findById(num) // 넘값을 찾아서
      .map(this::entityToDto)// 있으면 변환
      .orElseThrow(() -> new RuntimeException("Note not found: " + num)); // 없으면 예외처리
  }

  @Override
  public void modify(NoteDto noteDto) {
    Long num = noteDto.getNum();
    Optional<Note> result = noteRepository.findById(num);
    if (result.isPresent()) {
      Note note = result.get();
      note.changeTitle(noteDto.getTitle());
      note.changeContent(noteDto.getContent());
      noteRepository.save(note);
    }
  }

  @Override
  public void remove(Long num) {
    noteRepository.deleteById(num);
  }

  @Override
  public List<NoteDto> getAllWithWriter(String writerEmail) {
    List<Note> noteList = noteRepository.getList(writerEmail);
    return noteList.stream()
      .map(note -> entityToDto(note))
      .collect(Collectors.toList());
  }

  // 페이지 처리
  @Override
  public PageResponseDTO<NoteDto, Note> getList(String email, int page, int size, Sort sort) {
    // PageRequest.of(페이지 번호, 페이지 크기) → Pageable 객체 생성 ( 인덱스 0으로 시작해서 -1)
    PageRequest pageable = PageRequest.of(page -1, size, sort);// 전달받은 정렬 옵션 사용
    // jpa 기본 쿼리 findAll
    Page<Note> result = noteRepository.findByWriter_Email(email, pageable);

    // Page<Note> → PageResponseDTO<NoteDto, Note>
    // Note 객체를 받아서 NoteDto로 바꿔주는 함수 => this::entityToDto
    return PageResponseDTO.of(result, this::entityToDto, sort);
  }

  // 검색 조회

  @Override
  public List<NoteDto> search(NoteSearchDTO searchDTO) {
    return searchByType(noteRepository, searchDTO)
      .stream()
      .map(this::entityToDto)
      .toList();
  }
}
