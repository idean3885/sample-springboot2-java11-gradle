package com.example.samplespringboot2javagradle.service.member;

import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import com.example.samplespringboot2javagradle.dto.member.MemberChangePasswordReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberUserRspDto;
import com.example.samplespringboot2javagradle.exception.member.MemberBadRequestException;
import com.example.samplespringboot2javagradle.exception.member.MemberNotFoundException;
import com.example.samplespringboot2javagradle.repository.member.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 *
 * <h3>Member User Service</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class MemberUserServiceImpl implements MemberUserService {

  private final MemberJpaRepository memberJpaRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public MemberUserRspDto get(Long id) {
    var member =
        memberJpaRepository
            .findById(id)
            .filter(m -> MemberStatus.ACTIVE.equals(m.getStatus()))
            .orElseThrow(() -> MemberNotFoundException.notFoundOrWithdraw(id));
    return new MemberUserRspDto(member);
  }

  @Transactional
  @Override
  public MemberUserRspDto changePassword(Long id, MemberChangePasswordReqDto memberReqDto) {
    var member =
        memberJpaRepository
            .findById(id)
            .filter(m -> MemberStatus.ACTIVE.equals(m.getStatus()))
            .orElseThrow(() -> MemberNotFoundException.notFoundOrWithdraw(id));
    if (!passwordEncoder.matches(memberReqDto.getCurrentPassword(), member.getPassword())) {
      throw MemberBadRequestException.passwordNotMatched(id);
    }
    var encodedPassword = passwordEncoder.encode(memberReqDto.getNewPassword());
    member = member.toBuilder().password(encodedPassword).build();
    var updatedMember = memberJpaRepository.save(member);
    return new MemberUserRspDto(updatedMember);
  }

  @Override
  public MemberUserRspDto withdraw(Long id) {
    var member =
        memberJpaRepository
            .findById(id)
            .filter(m -> MemberStatus.ACTIVE.equals(m.getStatus()))
            .orElseThrow(() -> MemberNotFoundException.notFoundOrWithdraw(id));
    member = member.toBuilder().status(MemberStatus.WITHDRAW).build();
    return new MemberUserRspDto(memberJpaRepository.save(member));
  }
}
