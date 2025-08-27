package com.imchobo.club.security.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecureDigestAlgorithm;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {
  // 글자수를 32자 이상을 권장 (H256에서)
  private String secretKeyString = "club12345678club12345678club12345678club12345678club12345678club12345678club12345678club12345678club12345678club12345678"; // 실제 운영환경에서는 환경변수나 설정 파일로 관리하세요.
  private long expire = 60 * 24 * 30; // 30일 (단위: 분)
// 만들땐 key값
  // 토큰 생성
  public String generateToken(String content) throws Exception {
    SecureDigestAlgorithm alg = Jwts.SIG.HS256; // 전체 데이터를 가지고 암호화
    byte[] keyBytes = secretKeyString.getBytes(); // 바이트로 바꿔서 리턴해야 함
    Key key = Keys.hmacShaKeyFor(keyBytes);

    // Header를 통해 전달되는 값들을 Claims 클래스에 담아서 jwts에 전달
    Claims claims = Jwts.claims()
      .subject(content) // 아이디 값
      .issuedAt(new Date())
      .expiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant()))
      .build();

    return Jwts.builder()
      .claims(claims)
      .signWith(key , alg) // 암호화
      .compact();
  }

  // 토큰 유효성 검사 (유효한지 아닌지 검증)
  public String validateAndExtract (String tokenStr) throws Exception {
    // 검사할때는 Claims
    // 검증할 때는 시크릿 키
    byte [] keyBytes = secretKeyString.getBytes();
    SecretKey key = Keys.hmacShaKeyFor(keyBytes);

    Claims claims = Jwts.parser()
      .verifyWith(key)
      .build()
      .parseSignedClaims(tokenStr)// sign만 키를 암호화 시켜준거라 풀어준거
      .getPayload();
// 클레임스로 파싱한 다음에 이메일 값 전달
    return claims.getSubject();
  }
}