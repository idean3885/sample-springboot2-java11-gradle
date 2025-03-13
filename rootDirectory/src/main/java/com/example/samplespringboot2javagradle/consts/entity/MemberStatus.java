package com.example.samplespringboot2javagradle.consts.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 *
 * <h3>Member status</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public enum MemberStatus {
  ACTIVE(1L),
  WITHDRAW(2L),
  ;
  private final Long code;

  public static MemberStatus valueOf(Long code) {
    for (MemberStatus status : values()) {
      if (status.code.equals(code)) {
        return status;
      }
    }
    throw new IllegalArgumentException("No matching constant for [" + code + "]");
  }
}
