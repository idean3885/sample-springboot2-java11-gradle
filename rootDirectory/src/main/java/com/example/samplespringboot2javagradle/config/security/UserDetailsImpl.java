package com.example.samplespringboot2javagradle.config.security;

import com.example.samplespringboot2javagradle.consts.entity.MemberRole;
import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import com.example.samplespringboot2javagradle.entity.member.Member;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {

  private final Member member;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    var memberRoleList = member.getRoleList();
    return memberRoleList.stream()
        .map(MemberRole::name)
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return member.getPassword();
  }

  @Override
  public String getUsername() {
    return member.getEmail();
  }

  @Override
  public boolean isAccountNonExpired() {
    return MemberStatus.ACTIVE.equals(member.getStatus());
  }

  @Override
  public boolean isAccountNonLocked() {
    return MemberStatus.ACTIVE.equals(member.getStatus());
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return MemberStatus.ACTIVE.equals(member.getStatus());
  }

  @Override
  public boolean isEnabled() {
    return MemberStatus.ACTIVE.equals(member.getStatus());
  }
}
