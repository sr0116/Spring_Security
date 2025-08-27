package com.imchobo.club.repository;

import com.imchobo.club.entity.ClubMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {

  // 쿼리로 실행 하는 법
  @EntityGraph (attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
  @Query("select m from ClubMember m where m.fromSocial=:social and m.email=:email ")
  Optional<ClubMember> findByEmail(String email, boolean social);
// 쿼리 없이 사용하는 법 (JPA 로직으로 구성)

  @EntityGraph (attributePaths = {"roleSet"},
    type = EntityGraph.EntityGraphType.LOAD)
  Optional<ClubMember> findByEmailAndFromSocial(String email, boolean social);
  // FromSocial 클럽 멤버에 함수 이름을 수정해서 메서드명 작성
}
