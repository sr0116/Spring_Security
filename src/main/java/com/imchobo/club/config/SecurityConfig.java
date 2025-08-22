package com.imchobo.club.config;

import com.imchobo.club.security.handler.ClubLoginSuccessHandler;
import com.imchobo.club.security.service.ClubUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

  @Autowired
  private ClubUserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable()) // CSRF 보호 해제
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/favicon.ico", "/css/**", "/js/**", "/images/**").permitAll() // 정적 리소스 허용
        .requestMatchers("/error").permitAll()
        .requestMatchers("/sample/all").permitAll()
        .requestMatchers("/member/modify", "/member/modify/**").hasRole("USER")
        .requestMatchers("/sample/admin").hasRole("ADMIN")
        .requestMatchers("/sample/member").hasRole("USER")
        .anyRequest().authenticated()
      )
      .formLogin(form -> form
        .defaultSuccessUrl("/sample/all", false)
      )
      .logout(Customizer.withDefaults())
      .oauth2Login(form -> form
        .defaultSuccessUrl("/sample/all", false)
        .successHandler(successHandler())
      );

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ClubLoginSuccessHandler successHandler() {
    return new ClubLoginSuccessHandler(passwordEncoder());
  }
}
