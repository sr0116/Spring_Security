package com.imchobo.club.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Note  extends BaseEntity{
  // 사용자가 메모를 기록하는 용도
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long num;

  private String title;
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  private ClubMember writer;

  public void changeTitle(String title) {
    this.title = title;
  }
  public void changeContent(String content) {
    this.content = content;
  }
}
