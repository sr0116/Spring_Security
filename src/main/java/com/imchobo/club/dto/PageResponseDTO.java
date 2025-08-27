package com.imchobo.club.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;

@Data
@Builder
public class PageResponseDTO<D, E> { // 디티오 , 엔티티 써주면 그 순서대로
  private List<D> dtoList; // 실제 데이터 목록
  private int page;// 현재 페이지
  private int size;// 페이지 크기
  private Long total; // 전체 게시글 수
  private int totalPages; // 전체 페이지 수
  private String sortBy;     // 정렬 기준 컬럼명
  private String direction;  // asc / desc

  // ENTITY -> DTO 변환
  public static <D, E> PageResponseDTO<D, E> of(Page<E> pageResult, Function<E, D> fn, Sort sort) {
    Sort.Order order = sort.stream().findFirst().orElse(Sort.Order.desc("id"));

    return PageResponseDTO.<D, E>builder()
      .dtoList(pageResult.stream().map(fn).toList())
      .page(pageResult.getNumber() + 1)   // 0-based → 1-based
      .size(pageResult.getSize())
      .total(pageResult.getTotalElements())
      .totalPages(pageResult.getTotalPages())
      .sortBy(order.getProperty())        // 정렬 기준
      .direction(order.getDirection().name().toLowerCase()) // asc / desc
      .build();
  }
}
