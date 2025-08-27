package com.imchobo.club.controller;

import com.imchobo.club.dto.NoteDto;
import com.imchobo.club.entity.Note;
import com.imchobo.club.repository.NoteRepository;
import com.imchobo.club.service.NoteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/notes/")
public class NoteController {

  private final NoteService  noteService;

//  @PostMapping("")
//  public ResponseEntity<Long> save(@RequestBody NoteDto noteDto){
//    log.info("==================");
//    log.info("Note DTO ==>{} " , noteDto);
//
//    Long num = noteService.register(noteDto);
//    return new ResponseEntity<>(num, HttpStatus.OK);
//  }

//  public ResponseEntity<String> 프론트에게
// 답변 타입이라 생각하면 됨
//  @PostMapping("")  // String 타입으로 할때 버전
//  public ResponseEntity<String> save(@RequestBody NoteDto noteDto){
//    log.info("==================");
//    log.info("Note DTO ==>{} " , noteDto);
//
//    Long num = noteService.register(noteDto);
//    String message = num + "번 글이 등록 되었습니다.";
//    return  ResponseEntity.ok(message);
//  }

  // created 버전
  @PostMapping("")
  public ResponseEntity<NoteDto> save(@RequestBody NoteDto noteDto, HttpServletRequest request) {
    Long num = noteService.register(noteDto);

    NoteDto saved = NoteDto.builder()
      .num(num)
      .title(noteDto.getTitle())
      .content(noteDto.getContent())
      .writerEmail(noteDto.getWriterEmail())
      .build();

    // 절대 경로 생성 (요청 정보 기반)
    String baseUrl = request.getRequestURL().toString(); // 예: http://localhost:8080/notes
    URI location = URI.create(baseUrl + "/" + num);

    return ResponseEntity.created(location).body(saved);
  }


  // 특정 번호의 NOTE 확인하기
  @GetMapping(value = "/{num}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<NoteDto> read(@PathVariable("num") Long num){
    log.info("==================");
    log.info(num);
    return new ResponseEntity<>(noteService.get(num), HttpStatus.OK);
  }

  // 특정 회원의 모든 NOTE 확인 하기
  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE) //@RequestParam("email") 같으면 생략 가능
  public ResponseEntity<List<NoteDto>> getList(@RequestParam("email") String email){
    log.info("==================");
    log.info(email);
    return new ResponseEntity<>(noteService.getAllWithWriter(email), HttpStatus.OK);
  }

  @PutMapping(value = "/{num}", produces = MediaType.TEXT_PLAIN_VALUE)
  public ResponseEntity<String> modify(@RequestBody NoteDto noteDto, @PathVariable("num") Long num){
    log.info("=====================");
    log.info("Note DTO ==>{} " , noteDto);
    noteDto.setNum(num); //  PathVariable 사용하려면
    noteService.modify(noteDto);
    return ResponseEntity.ok("modified");
  }

  // 글 삭제
  @DeleteMapping(value = "{num}")
  public ResponseEntity<String> remove (@PathVariable("num") Long num) {
    log.info("====================");
    log.info("num");
    noteService.remove(num);
    return ResponseEntity.ok("removed");
  }
}
