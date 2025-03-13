package com.example.samplespringboot2javagradle.service.member;

import com.example.samplespringboot2javagradle.dto.member.MemberAdminRspDto;
import com.example.samplespringboot2javagradle.dto.member.MemberSaveReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberUpdateReqDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 *
 *
 * <h3>Member Service</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
public interface MemberAdminService {
  MemberAdminRspDto saveMember(MemberSaveReqDto reqDto);

  MemberAdminRspDto get(Long id);

  List<MemberAdminRspDto> findAllMembers();

  Page<MemberAdminRspDto> findPage(PageRequest pageRequest);

  MemberAdminRspDto updateMember(Long id, MemberUpdateReqDto reqDto);

  void deleteMember(Long id);
}
