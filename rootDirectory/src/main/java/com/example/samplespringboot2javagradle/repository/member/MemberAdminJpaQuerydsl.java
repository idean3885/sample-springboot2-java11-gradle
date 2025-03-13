/*
 * Copyright 2024 NHN (https://nhn.com) and others.
 * © NHN Corp. All rights reserved.
 */

package com.example.samplespringboot2javagradle.repository.member;

import com.example.samplespringboot2javagradle.dto.member.MemberAdminRspDto;
import com.example.samplespringboot2javagradle.entity.member.QMember;
import com.querydsl.core.types.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class MemberAdminJpaQuerydsl extends QuerydslRepositorySupport {
  public MemberAdminJpaQuerydsl() {
    super(QMember.class);
  }

  public Page<MemberAdminRspDto> findPage(Pageable pageable) {
    var table = QMember.member;
    var select =
        Projections.bean(
            MemberAdminRspDto.class,
            table.id,
            table.email,
            table.roleList,
            table.status,
            table.createdBy,
            table.lastModifiedBy,
            table.lastModifiedDate);

    var contents =
        from(table)
            .select(select)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    var total = from(table).fetchCount();
    return new PageImpl<>(contents, pageable, total);
  }
}
