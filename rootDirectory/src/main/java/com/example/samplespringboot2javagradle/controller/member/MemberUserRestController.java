package com.example.samplespringboot2javagradle.controller.member;

import com.example.samplespringboot2javagradle.config.security.UserDetailsImpl;
import com.example.samplespringboot2javagradle.dto.member.MemberChangePasswordReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberUserRspDto;
import com.example.samplespringboot2javagradle.service.member.MemberUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * <h3>Member User Rest Controller</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@PreAuthorize(
    "hasRole('#{T(com.example.samplespringboot2javagradle.config.security.RoleType).USER}')")
@RequestMapping("api/members/v1/user")
@RequiredArgsConstructor
@RestController
public class MemberUserRestController {

  private final MemberUserService memberUserService;

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
      })
  @Operation(summary = "회원 조회", description = "활성화된 회원만 조회 가능")
  @GetMapping
  public MemberUserRspDto get(
      @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return memberUserService.get(userDetails.getMember().getId());
  }

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
      })
  @Operation(summary = "비밀번호 수정", description = "활성화된 회원만 수정 가능")
  @PutMapping("password")
  public MemberUserRspDto changePassword(
      @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails,
      @Valid @RequestBody MemberChangePasswordReqDto reqDto) {
    return memberUserService.changePassword(userDetails.getMember().getId(), reqDto);
  }

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
      })
  @Operation(summary = "회원 탈퇴", description = "활성화된 회원만 탈퇴 가능, 모든 권한을 회수하고 상태를 탈퇴로 변경합니다.")
  @DeleteMapping
  public MemberUserRspDto withdraw(
      @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return memberUserService.withdraw(userDetails.getMember().getId());
  }
}
