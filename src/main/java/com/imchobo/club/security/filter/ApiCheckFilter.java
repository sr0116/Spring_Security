package com.imchobo.club.security.filter;

import com.imchobo.club.security.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {
  private JWTUtil jwtUtil;
// 클래스가 하나이면 오토와이어, 여러개면 bean
  private AntPathMatcher antPathMatcher;
  private String pattern;

  public ApiCheckFilter(String pattern, JWTUtil jwtUtil) {
    this.antPathMatcher = new AntPathMatcher();
    this.pattern = pattern;
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    log.info("RequestURI: {}" , request.getRequestURI());
    log.info(antPathMatcher.match( pattern, request.getRequestURI())); // 순서가 중요 앞에가 무조건 Pattern, 뒤에가 실제 경로
    // antPathMatcher 패턴의 uri가 뒤에 것이랑 맞는지

    if(antPathMatcher.match(pattern, request.getRequestURI())) {

      log.info("ApiCheckFilter---------------------");
      log.info("ApiCheckFilter---------------------");
      log.info("ApiCheckFilter---------------------");
      boolean checkHeader = checkAuthHeader(request);

      if(checkHeader) {
        filterChain.doFilter(request, response);
        return; // 없으면 filterChain.doFilter(request, response);이게 실행되버리니까 return 넣어줌
      }
      // 실패했을때
      else {
          response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 권한 부족
        response.setContentType("application/json;charset=UTF-8");
        
        // 헤더가 없을 때 보낼 메세지
        JSONObject json = new JSONObject();
        String message = "FAIL CHECK API TOKEN";
        json.put("code", 403);
        json.put("message", message);

        PrintWriter out = response.getWriter();
        out.print(json);
        return; // 시큐리티 필터 체인을 끝내라
      }
    }
    // 필터가 안 맞을 때
    filterChain.doFilter(request, response);
  }
  private boolean checkAuthHeader(HttpServletRequest request) {
    boolean checkResult = false;
// 임의값 저장해서 받아옴
    String authHeader = request.getHeader("Authorization");
//    if (StringUtils.hasText(authHeader)) {
//      log.info("Authorization exist: {}", authHeader);
//      if(authHeader.equals("12345678")) {
//        checkResult = true;
//      }
//    }
    // 토큰 확인 과정
    if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
      // 토큰에 Authorization 속성에 값이 담기는데 Bearer (공백) 으로 저장 (글자가 Bearer 들어가는지 확인)
      log.info("Authorization exist : {} ", authHeader);
      try { // "Bearer " 7번째부터
        String email = jwtUtil.validateAndExtract(authHeader.substring(7));
        log.info("validate result : {} ", email);
        checkResult =email.length() > 0;
      }catch (Exception e) {
        e.printStackTrace();
      }
    }
    return checkResult;
  }

}


