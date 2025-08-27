package com.imchobo.club.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {

  private Long num;
  private String title;
  private String content;
  private String writerEmail; // NoteRepository의 writer와 구분하기 위해
  private LocalDateTime regDate, modDate;
}
