package com.example.samplespringboot2javagradle

import org.springframework.security.test.context.support.WithSecurityContext

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithCustomSecurityContextFactory.class)
@interface WithCustomMockUser {

    String username()
    String[] roles()
}