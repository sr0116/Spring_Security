package com.imchobo.club.security;

import com.imchobo.club.entity.ClubMember;
import com.imchobo.club.entity.ClubMemberRole;
import com.imchobo.club.repository.ClubMemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.InputStream;
import java.util.stream.IntStream;

@SpringBootTest
public class ClubMemberTests {

  @Autowired
  private ClubMemberRepository repository;

  @Autowired
  private PasswordEncoder PasswordEncoder;

  @Test
  public void insertDummies () {
    // 1 - 80 까지는 User만 지정
    // 81 - 90 까지는 User, MENAGER 지정
    // 1 - 80 까지는 User, MENAGER ,ADMIN 지정
    IntStream.rangeClosed(1, 100).forEach(i -> {
      ClubMember clubMember = ClubMember.builder()
        .email("user" + i + "@gmail.com")
        .name("사용자" + i)
        .fromSocial(false)
        .password(PasswordEncoder.encode("1111"))
        .build();

      // Club
      clubMember.addMemberRole(ClubMemberRole.USER);

      if (i > 80) {
        clubMember.addMemberRole(ClubMemberRole.MANAGER);
      }

      if (i > 90) {
        clubMember.addMemberRole(ClubMemberRole.ADMIN);
      }
        repository.save(clubMember);
    });
  }
}
