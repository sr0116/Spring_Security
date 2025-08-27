package com.imchobo.club.security;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Log4j2
public class PasswordTests {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void testEncode() {
    String password = "1111";
    String enPw = passwordEncoder.encode(password);

    log.info("Encoded Password: {}", enPw);
    System.out.println(enPw + " enPw");

    // 인코딩된 비밀번호와 원본 비밀번호 비교
    boolean matchesResult = passwordEncoder.matches(password, enPw);
    log.info("Password Match Result: {}", matchesResult);
    System.out.println(matchesResult + " matchesResult");
  }


}
