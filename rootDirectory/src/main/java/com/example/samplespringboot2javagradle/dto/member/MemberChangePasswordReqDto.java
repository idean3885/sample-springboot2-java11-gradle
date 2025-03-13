package com.example.samplespringboot2javagradle.dto.member;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Member Req Dto
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberChangePasswordReqDto {

  @NotNull private String currentPassword;
  @NotNull private String newPassword;
}
