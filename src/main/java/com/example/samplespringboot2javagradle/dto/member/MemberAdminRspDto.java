package com.example.samplespringboot2javagradle.dto.member;

import com.example.samplespringboot2javagradle.consts.entity.MemberRole;
import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import com.example.samplespringboot2javagradle.entity.member.Member;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Member Response Dto
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@EqualsAndHashCode
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberAdminRspDto {

  private Long id;
  private String email;
  private List<MemberRole> roleList;
  private MemberStatus status;
  private Long createdBy;
  private LocalDateTime createdDate;
  private Long lastModifiedBy;
  private LocalDateTime lastModifiedDate;

  public MemberAdminRspDto(Member member) {
    this.id = member.getId();
    this.email = member.getEmail();
    this.roleList = member.getRoleList();
    this.status = member.getStatus();
    this.createdBy = member.getCreatedBy();
    this.createdDate = member.getCreatedDate();
    this.lastModifiedBy = member.getLastModifiedBy();
    this.lastModifiedDate = member.getLastModifiedDate();
  }
}
