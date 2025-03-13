package com.example.samplespringboot2javagradle.config.security;

import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import com.example.samplespringboot2javagradle.repository.member.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final MemberJpaRepository memberJpaRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return memberJpaRepository
        .findByEmailAndStatus(username, MemberStatus.ACTIVE)
        .map(UserDetailsImpl::new)
        .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다. username: " + username));
  }
}
