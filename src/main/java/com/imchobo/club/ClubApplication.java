package com.imchobo.club;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@Log4j2
@EnableJpaAuditing
public class ClubApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClubApplication.class, args);
    log.info("시큐리티 시작~~~~~~~~~~🤫");
  }

}
