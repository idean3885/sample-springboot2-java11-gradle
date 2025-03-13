package com.example.samplespringboot2javagradle.controller.member;

import com.example.samplespringboot2javagradle.dto.member.MemberAdminRspDto;
import com.example.samplespringboot2javagradle.dto.member.MemberSaveReqDto;
import com.example.samplespringboot2javagradle.dto.member.MemberUpdateReqDto;
import com.example.samplespringboot2javagradle.service.member.MemberAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *
 * <h3>Member Admin Rest Controller</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@RequestMapping("api/members/v1/admin")
@RequiredArgsConstructor
@RestController
public class MemberAdminRestController {

  private final MemberAdminService memberAdminService;

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
      })
  @Operation(summary = "회원 저장", description = "회원 데이터 저장 API")
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping
  public MemberAdminRspDto saveMember(@Valid @RequestBody MemberSaveReqDto memberReqDto) {
    return memberAdminService.saveMember(memberReqDto);
  }

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
      })
  @Operation(summary = "회원 조회", description = "회원 단건 조회(상태 구분X)")
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("{id}")
  public MemberAdminRspDto get(@PathVariable Long id) {
    return memberAdminService.get(id);
  }

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
      })
  @Operation(summary = "회원 전체 조회", description = "회원 전체 조회(상태 구분X)")
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping
  public List<MemberAdminRspDto> findAllMembers() {
    return memberAdminService.findAllMembers();
  }

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
      })
  @Operation(summary = "회원 페이징 조회")
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("page")
  public Page<MemberAdminRspDto> findPage(@RequestParam int page, @RequestParam int size) {
    var pageRequest = PageRequest.of(page, size);
    return memberAdminService.findPage(pageRequest);
  }

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
      })
  @Operation(summary = "회원 수정", description = "회원 데이터 수정 API")
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping("{id}")
  public MemberAdminRspDto updateMember(
      @PathVariable Long id, @RequestBody MemberUpdateReqDto reqDto) {
    return memberAdminService.updateMember(id, reqDto);
  }

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
      })
  @Operation(summary = "회원 삭제", description = "활성화된 회원만 삭제 가능, 삭제 시 상태 변경(하드 딜리트 X)")
  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping("{id}")
  public void deleteMember(@PathVariable Long id) {
    memberAdminService.deleteMember(id);
  }

  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
      })
  @Operation(summary = "강제 로그아웃", description = "api docs 에서 어드민 사용자가 로그아웃하기 위한 API")
  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("logout")
  public boolean logout(HttpServletRequest request) {
    var session = request.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return true;
  }
}
