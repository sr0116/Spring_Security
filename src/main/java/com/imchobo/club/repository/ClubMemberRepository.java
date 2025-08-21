package com.imchobo.club.repository;

import com.imchobo.club.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {
}
