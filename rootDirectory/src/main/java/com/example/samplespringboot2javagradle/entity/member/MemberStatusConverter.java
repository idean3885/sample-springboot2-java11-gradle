/*
 * Copyright 2024 NHN (https://nhn.com) and others.
 * © NHN Corp. All rights reserved.
 */

package com.example.samplespringboot2javagradle.entity.member;

import com.example.samplespringboot2javagradle.consts.entity.MemberStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 *
 * <h3>Member status converter</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Converter
public class MemberStatusConverter implements AttributeConverter<MemberStatus, Long> {

  @Override
  public Long convertToDatabaseColumn(MemberStatus attribute) {
    return attribute.getCode();
  }

  @Override
  public MemberStatus convertToEntityAttribute(Long dbData) {
    return MemberStatus.valueOf(dbData);
  }
}
