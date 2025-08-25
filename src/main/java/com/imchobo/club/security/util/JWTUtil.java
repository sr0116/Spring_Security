package com.imchobo.club.security.util;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;

@Log4j2
public class JWTUtil {

  private String secretKey = "club12345678"; // 실제 운영환경에서는 환경변수나 설정 파일로 관리하세요.
  private long expire = 60 * 24 * 30; // 30일 (단위: 분)

  // 토큰 생성
//  public String generateToken(String content) throws Exception {
//    return Jwts.builder()
//      .issuedAt(new Date()) // 발급 시간
//      .expiration(Date.from(ZonedDateTime.now().plusMinutes(expire).toInstant())) // 만료 시간
//      .claim("sub", content) // payload에 content 저장
//      .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8)) // 서명
//      .compact();
//  }
//  public String validateAndExtract (String tokenStr) throws Exception {
//    String contentValue = null;
//    try {
//      // 토큰 파싱 및 검증
//      Jws<Claims> jws = Jwts.parser()
//        .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
//        .build().parseClaimsJws(tokenStr);
//
//      // payload 추출 (sub 클레임)
//      Claims claims = jws.getBody();
//      contentValue = claims.get("sub", String.class);
//
//      log.info("JWT payload(sub): {}", contentValue);
//
//    } catch (ExpiredJwtException e) {
//      log.error("JWT 토큰이 만료되었습니다", e);
//      throw e;
//    } catch (JwtException e) {
//      log.error("JWT 토큰이 유효하지 않습니다", e);
//      throw e;
//    }
//
//    return contentValue;
//  }
}