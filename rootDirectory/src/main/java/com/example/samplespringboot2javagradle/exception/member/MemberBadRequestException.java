/*
 * Copyright 2024 NHN (https://nhn.com) and others.
 * © NHN Corp. All rights reserved.
 */

package com.example.samplespringboot2javagradle.exception.member;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MemberBadRequestException extends RuntimeException {

  public MemberBadRequestException(String message) {
    super(message);
  }

  public static MemberBadRequestException passwordNotMatched(long id) {
    return new MemberBadRequestException("Password is not match. id: " + id);
  }
}
