package com.example.samplespringboot2javagradle.dto.member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 *
 * <h3>Member Update Req Dto</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberUpdateReqDto {

  private String email;
  private String password;
}
