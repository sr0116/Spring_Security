package com.imchobo.club.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// Cross-Origin-Resource Sharing
// 서버의 주소 또는 포트가 다른 경우 데이터를 주고 받을 수 있도록 함
// 가장 먼저 지정 -> 컨피그 전에 들어간다

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORSFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    // 허용하고자하는 주소(url) 를 설정
    // * : 모든 주소 허용
    // 2번째 인자에 스트링 한개의 값만 허용(2개 허용 안함) 외부 포트 하나만 허용
    // 2개 사용하고 싶으면 배열형태로 만들어서 가능
    // 허용할 Origin(출처, 도메인) 목록을 미리 지정
// - 프론트엔드 서버 주소들을 배열로 저장
// - 예: React 개발 서버 (3000, 4000 포트)
//    List<String> allowedOrigins = Arrays.asList("http://localhost:3000", "http://localhost:4000");
//  클라이언트 요청 헤더에서 "Origin" 값 추출
// - 브라우저가 요청 보낼 때 자동으로 붙이는 값
// - 예: "http://localhost:3000"
//    String originHeader = request.getHeader("Origin");

//  요청한 Origin이 허용된 목록 안에 있는지 검사
// - 만약 목록에 없다면 CORS 차단 (Access-Control-Allow-Origin 안 내려감)
//    if (allowedOrigins.contains(originHeader)) {
    //  응답 헤더에 Access-Control-Allow-Origin 추가
    // - 브라우저에게 "이 서버는 해당 Origin 요청 허용한다" 알림
    // - 동적으로 OriginHeader 값을 그대로 넣어줌
//      response.setHeader("Access-Control-Allow-Origin", originHeader);
//    }
    response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
    // 토큰을 사용할 것인가 (사용할 거면 true) 인증 관련
    response.setHeader("Access-Control-Allow-Credentials", "true");
    // 클라이언트에서 get 요청을 받았을 때  메서드 허용 여부
    response.setHeader("Access-Control-Allow-Methods", "*");
    // 지속 시간 , preflight
    // Options로 먼저 데이터를 전송하여 사전확인 작업
    // POST /api/data HTTP/1.1
    //Origin : http://localhost:3000
    // Content-Type: application/json
    // Authorization: Bearer ~~~
// 보내려는 값 -> OPTIONS로 먼저 보냄(데이터의 내용은 상관없고 이런 형식으로 보낼것) -> 사전 데이터
//    Options/api/data HTTP/1.1
    //Origin : http://localhost:3000
    // Content-Type: application/json
    // Authorization: Bearer ~~~
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "*");
    //Origin : 요청 주소, 포트
    // X-Requested-With : Ajax 요청(비동기)-> XMLhttpRequest
    //Content-Type: Body 내용의 데이터 형식
//    Accept : 응답을 받을 때 데이터 형식(application/json, tcxt/html, text/lain)
    // key : 사용자 헤더(브라우저가 생성하는 것 아님)
//    Authorization : 인증정보
if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
  response.setStatus(HttpServletResponse.SC_OK);
}else  {
filterChain.doFilter(request,response);
}


  }
}
