package com.example.samplespringboot2javagradle.dto.member;

import com.example.samplespringboot2javagradle.consts.entity.MemberRole;
import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import com.example.samplespringboot2javagradle.entity.member.Member;
import java.util.List;
import javax.validation.constraints.Email;
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
public class MemberSaveReqDto {

  @NotNull @Email private String email;

  @NotNull private String password;

  public Member toEntity() {
    return Member.builder()
        .email(email)
        .password(password)
        .roleList(List.of(MemberRole.ROLE_USER))
        .status(MemberStatus.ACTIVE)
        .build();
  }
}
