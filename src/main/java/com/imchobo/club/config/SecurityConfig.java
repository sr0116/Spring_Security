package com.imchobo.club.config;


import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(); // 스프링 부트가 기본 적으로 제공하는 incode
  }

  @Bean
  public InMemoryUserDetailsManager inDetailsService() {
    UserDetails user = User.builder()
      .username("user1")
      .password(passwordEncoder().encode("1111"))
      .roles("Member")
      .build();

    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // spring security 6이상버전에서 아래와 같이 변경(교재와 차이가 있습니다)
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests((auth) -> {
        auth.requestMatchers("/sample/all").permitAll()   // 누구나 로그인 없이 접근 가능
          .requestMatchers("/sample/member").hasRole("USER"); // USER 권한만 접근 가능
      })
      .formLogin(Customizer.withDefaults()) // 로그인
      .logout(Customizer.withDefaults()); // 로그아웃

    return http.build();
  }
//  @Bean
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//      .authorizeHttpRequests(auth -> auth
//        .requestMatchers("/sample/all").permitAll()         // 모두 접근 허용
//        .requestMatchers("/sample/member").authenticated()  // 로그인 필요
//        .requestMatchers("/sample/admin").hasRole("ADMIN")  // 관리자만
//        .anyRequest().authenticated()                       // 나머지 전부 로그인 필요
//      )
//      .formLogin(Customizer.withDefaults())   // 기본 로그인 페이지
//      .logout(Customizer.withDefaults());     // 기본 로그아웃
//    return http.build();
//  }

}