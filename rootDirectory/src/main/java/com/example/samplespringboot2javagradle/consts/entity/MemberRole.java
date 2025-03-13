package com.example.samplespringboot2javagradle.consts.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {
  ROLE_ADMIN(new String[] {"READ_ALL", "WRITE_ALL", "UPDATE_ALL", "DELETE_ALL"}),
  ROLE_USER(new String[] {"READ_ACCESSIBLE", "WRITE_ACCESSIBLE", "UPDATE_ACCESSIBLE"}),
  ;

  private final String[] authorities;

  public String getRoleName() {
    return this.name().replace("ROLE_", "");
  }
}
