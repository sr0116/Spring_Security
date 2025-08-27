package com.imchobo.club.service;

import com.imchobo.club.dto.NoteDto;
import com.imchobo.club.entity.Note;
import com.imchobo.club.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
  @Autowired
  private NoteRepository noteRepository;

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
    Optional<Note> result = noteRepository.getWithWriter(num);
    if (result.isPresent()) {
      return entityToDto(result.get());
    }
    return null;
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

}
