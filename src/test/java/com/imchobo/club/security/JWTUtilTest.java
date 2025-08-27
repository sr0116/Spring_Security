package com.imchobo.club.security;

import com.imchobo.club.security.util.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JWTUtilTest {

private JWTUtil jwtUtil;

  @BeforeEach// JWTTest를 테스트 할 때마다 먼저 실행되는 메서드
  public void beforeEach() {
    jwtUtil  = new JWTUtil(); //토큰 생성
  }

  @Test
  public void testEncoder ()throws  Exception {
    String email = "user97@gmail.com";
    String str = jwtUtil.generateToken(email);

    System.out.println(str);

  }
}
