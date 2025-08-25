package com.imchobo.club.security.service;

import com.imchobo.club.entity.ClubMember;
import com.imchobo.club.entity.ClubMemberRole;
import com.imchobo.club.repository.ClubMemberRepository;
import com.imchobo.club.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ClubOAuth2UserDetailsService extends DefaultOAuth2UserService {


  private final ClubMemberRepository repository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    String clientName = userRequest.getClientRegistration().getClientName();
    log.info("clientName={}", clientName);

    OAuth2User oAuth2User = super.loadUser(userRequest);
    log.info("attributes={}", oAuth2User.getAttributes());

    String email = null;
    if ("Google".equals(clientName)) {
      email = oAuth2User.getAttribute("email");
    }
    log.info("email={}", email);

    ClubMember member = saveSocialMember(email);

    List<GrantedAuthority> authorities = member.getRoleSet().stream()
      .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
      .collect(Collectors.toList());

    ClubAuthMemberDTO clubAuthMember = new ClubAuthMemberDTO(
      member.getEmail(),
      member.getPassword(),
      true,
      authorities,
      oAuth2User.getAttributes()
    );
    clubAuthMember.setName(member.getName());

    return clubAuthMember;
  }

  private ClubMember saveSocialMember(String email) {
    Optional<ClubMember> result = repository.findByEmail(email, true);

    if (result.isPresent()) {
      return result.get();
    }

    ClubMember member = ClubMember.builder()
      .email(email)
      .name(email)
      .password(passwordEncoder.encode("1111"))
      .fromSocial(true)
      .build();

    member.addMemberRole(ClubMemberRole.USER);
    repository.save(member);

    return member;
  }
}
