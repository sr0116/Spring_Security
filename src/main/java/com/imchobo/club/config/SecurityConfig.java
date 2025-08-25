package com.imchobo.club.config;

import com.imchobo.club.security.filter.ApiCheckFilter;
import com.imchobo.club.security.filter.ApiLoginFilter;
import com.imchobo.club.security.handler.ClubLoginSuccessHandler;
import com.imchobo.club.security.service.ClubUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

  @Autowired
  private ClubUserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager ) throws Exception {
    ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login");
    apiLoginFilter.setAuthenticationManager(authenticationManager);

    http
      .csrf(csrf -> csrf.disable()) // CSRF 보호 해제
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/favicon.ico", "/css/**", "/js/**", "/images/**").permitAll() // 정적 리소스 허용
        .requestMatchers("/error").permitAll()
        .requestMatchers("/sample/all").permitAll()
        .requestMatchers("/notes/**").permitAll()
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
      ) // 60 * 60 * 24 하루
      .rememberMe(token -> token.tokenValiditySeconds(60 * 2))
      .addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class)
      .addFilterBefore(apiLoginFilter, UsernamePasswordAuthenticationFilter.class);

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

  @Bean
  public ApiCheckFilter apiCheckFilter() {
    // /note/한 글자로도 있어야 합니다
    return new ApiCheckFilter("/notes/**/*");
  }

//  @Bean
//  public ApiLoginFilter apiLoginFilter(AuthenticationManagerBuilder authenticationManagerBuilder) {
//    ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/api/login");
//    apiLoginFilter.setAuthenticationManager(authenticationManager());
//    return  apiLoginFilter();
//  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws  Exception {
    return config.getAuthenticationManager();
  }

}
