package com.imchobo.club.repository;

import com.imchobo.club.entity.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
  @EntityGraph(attributePaths = "writer", type = EntityGraph.EntityGraphType.LOAD)
  @Query("select n from Note n where n.num = :num")
  Optional<Note> getWithWriter(Long num);

  @EntityGraph(attributePaths = "writer", type = EntityGraph.EntityGraphType.LOAD)
  @Query("select n from Note n where n.writer.email = :email order by n.num desc")
  List<Note> getList(String email);

  Page<Note> findByWriter_Email(String email, Pageable pageable);
// 검색필터
  List<Note> findByTitleContaining(String keyword);
  List<Note> findByContentContaining(String keyword);
  List<Note> findByTitleContainingOrContentContaining(String keywordTitle, String keywordContent);

}
