package com.example.samplespringboot2javagradle

import com.example.samplespringboot2javagradle.consts.entity.MemberRole
import org.springframework.security.test.context.support.WithSecurityContext

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomSecurityContextFactory.class)
@interface WithCustomMockUser {

    String username()

    MemberRole[] roleList()
}