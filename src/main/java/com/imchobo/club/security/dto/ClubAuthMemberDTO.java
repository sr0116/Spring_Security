package com.imchobo.club.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthMemberDTO extends User implements OAuth2User {

  private String email;
  private String name;
  private boolean fromSocial;
  private Map<String, Object> attr;
  private String password;

  public ClubAuthMemberDTO(
    String username,
    String password,
    boolean fromSocial,
    Collection<? extends GrantedAuthority> authorities,
    Map<String, Object> attr
  ) {
    this(username, password, fromSocial, authorities);
    this.attr = attr;
  }

  public ClubAuthMemberDTO(
    String username,
    String password,
    boolean fromSocial,
    Collection<? extends GrantedAuthority> authorities
  ) {
    super(username, password, authorities);
    this.password  = password;
    this.email = username;
    this.fromSocial = fromSocial;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attr == null ? Map.of() : attr; // 소셜 로그인 정보가 없어서 오류가 나는거라
  }
  // 가져온 속성이 틀려서 get으로 따로 가져옴
  @Override
  public String getName () {
    return  attr != null && attr.get("email")  != null ? (String)  attr.get("email") : email;
  } // getname 을 했을 때 무조건 이메일을 가져옴
  // 소셜 정보가 디비에 저장 안 되어 있을 때 attr에서 가져오고
  //디비에 저장 되어있으면 디비에서 가져온 값으로 사용해도 된다

  @Override
  public String getPassword () {
    return  password;
  }

}
