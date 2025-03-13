package com.example.samplespringboot2javagradle.service.member;

import com.example.samplespringboot2javagradle.consts.entity.MemberRole;
import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import com.example.samplespringboot2javagradle.dto.member.MemberAdminRspDto;
import com.example.samplespringboot2javagradle.dto.member.MemberSaveReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberUpdateReqDto;
import com.example.samplespringboot2javagradle.exception.member.MemberNotFoundException;
import com.example.samplespringboot2javagradle.repository.member.MemberAdminJpaQuerydsl;
import com.example.samplespringboot2javagradle.repository.member.MemberJpaRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Member Service
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@RequiredArgsConstructor
@Service
public class MemberAdminAdminServiceImpl implements MemberAdminService {

  private final MemberJpaRepository memberJpaRepository;
  private final PasswordEncoder passwordEncoder;
  private final MemberAdminJpaQuerydsl memberAdminJpaQuerydsl;

  @Override
  public MemberAdminRspDto saveMember(MemberSaveReqDto memberReqDto) {
    var encodedPassword = passwordEncoder.encode(memberReqDto.getPassword());
    var roleList = List.of(MemberRole.ROLE_USER);
    var member =
        memberReqDto.toEntity().toBuilder().password(encodedPassword).roleList(roleList).build();
    var savedMember = memberJpaRepository.save(member);
    return new MemberAdminRspDto(savedMember);
  }

  @Override
  public MemberAdminRspDto get(Long id) {
    return memberJpaRepository
        .findById(id)
        .map(MemberAdminRspDto::new)
        .orElseThrow(() -> MemberNotFoundException.notFoundOrWithdraw(id));
  }

  @Override
  public List<MemberAdminRspDto> findAllMembers() {
    var members = new ArrayList<MemberAdminRspDto>();
    memberJpaRepository.findAll().forEach(member -> members.add(new MemberAdminRspDto(member)));
    return members;
  }

  @Override
  public Page<MemberAdminRspDto> findPage(PageRequest pageRequest) {
    return memberAdminJpaQuerydsl.findPage(pageRequest);
  }

  @Override
  public MemberAdminRspDto updateMember(Long id, MemberUpdateReqDto reqDto) {
    var member =
        memberJpaRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Member is not exist. id: " + id));

    if (StringUtils.isNoneBlank(reqDto.getEmail())) {
      member = member.toBuilder().email(reqDto.getEmail()).build();
    }
    if (StringUtils.isNoneBlank(reqDto.getPassword())) {
      var encodedPassword = passwordEncoder.encode(reqDto.getPassword());
      member = member.toBuilder().password(encodedPassword).build();
    }
    return new MemberAdminRspDto(memberJpaRepository.save(member));
  }

  @Transactional
  @Override
  public void deleteMember(Long id) {
    var member =
        memberJpaRepository
            .findById(id)
            .filter(m -> MemberStatus.ACTIVE.equals(m.getStatus()))
            .orElseThrow(() -> new IllegalArgumentException("Member is not exist. id: " + id));
    memberJpaRepository.delete(member);
  }
}
