# ✔ Spring Security 정리 (구멍가게 코딩단 ver.)
## 기본 보안 설정

### SecurityConfig
- `@EnableWebSecurity` 적용 → Spring Security 활성화
- `SecurityFilterChain` Bean 등록 → HTTP 보안 규칙 정의
- `/api/**` 요청 → 인증 필수
- `/api/login` 요청 → 로그인 전용 API 엔드포인트
- `PasswordEncoder` → `BCryptPasswordEncoder` 사용하여 안전한 비밀번호 암호화 처리

---

##  사용자 인증 처리

### ApiLoginFilter
- `AbstractAuthenticationProcessingFilter` 상속
- 로그인 요청 시 `email` / `pw` 파라미터 추출
- `UsernamePasswordAuthenticationToken` 생성 → `AuthenticationManager`에게 전달
- 정상 인증 시 이후 JWT 발급 준비

### ClubUserDetailsService
- DB에서 사용자(email) 조회
- 조회한 데이터를 `UserDetails` 객체로 변환 → Security 내부 인증 객체에서 활용
- 사용자 권한(Role) → `SimpleGrantedAuthority`로 변환하여 Spring Security 권한 시스템에 맞춤

---

##  JWT 기반 인증

### JwtUtil
- `generateToken(String content)` → JWT 발급
  - 발급 시간(`issuedAt`), 만료 시간(`expiration`), payload(`sub`) 포함
- `validateAndExtract(String token)` → 토큰 유효성 검증 및 payload 추출
  - 만료(`ExpiredJwtException`) 또는 위조(`JwtException`) 시 예외 발생

### ApiCheckFilter
- `OncePerRequestFilter` 상속 → 모든 요청마다 단 한 번 실행
- `AntPathMatcher` 로 특정 URL 패턴 매칭
- HTTP 헤더에서 JWT 추출 → `JwtUtil.validateAndExtract`로 검증
- JWT가 유효하지 않으면 `AccessDenied` 처리

---

## 소셜 로그인 (OAuth2)

### ClubOAuth2UserDetailsService
- `DefaultOAuth2UserService` 상속
- 소셜 로그인 사용자 정보 로드
- DB 조회 → 회원 존재 여부 확인
- 신규 회원일 경우 → 회원 생성 로직 추가 예정
- 권한(Role) 부여 후 → Spring Security 인증 객체로 변환

---

##  오늘까지 정리 (구멍가게 코딩단 진도)

✔ SecurityConfig 작성 → 전체 보안 흐름 기본 골격 완성  
✔ ApiLoginFilter 구현 → 이메일/비밀번호 로그인 요청 처리  
✔ ApiCheckFilter 구현 → JWT 토큰 검증 필터 추가  
✔ JwtUtil 작성 → JWT 발급 및 검증 유틸리티  
✔ ClubUserDetailsService 작성 → DB 사용자 인증 로직 구현  
✔ ClubOAuth2UserDetailsService 작성 → 소셜 로그인 사용자 매핑 처리  
