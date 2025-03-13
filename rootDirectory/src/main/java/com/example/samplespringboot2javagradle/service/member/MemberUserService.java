package com.example.samplespringboot2javagradle.service.member;

import com.example.samplespringboot2javagradle.dto.member.MemberChangePasswordReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberUserRspDto;

/**
 *
 *
 * <h3>Member User Service</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
public interface MemberUserService {

  MemberUserRspDto get(Long id);

  MemberUserRspDto changePassword(Long id, MemberChangePasswordReqDto memberReqDto);

  MemberUserRspDto withdraw(Long id);
}
