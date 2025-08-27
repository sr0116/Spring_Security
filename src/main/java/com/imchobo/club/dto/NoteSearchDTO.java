package com.imchobo.club.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoteSearchDTO {
  private String keyword; // 검색어
  private SearchType type;

}
