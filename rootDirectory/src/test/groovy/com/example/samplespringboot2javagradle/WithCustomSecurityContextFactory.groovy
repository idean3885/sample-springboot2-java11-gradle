package com.example.samplespringboot2javagradle

import com.example.samplespringboot2javagradle.config.security.UserDetailsImpl
import com.example.samplespringboot2javagradle.fixture.MemberFixture

import java.util.stream.Collectors
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithCustomSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {
    @Override
    SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        var username = annotation.username()
        var roleList = annotation.roleList()
        var user = MemberFixture.userDetails(username, roleList)
        var userDetailsImpl = new UserDetailsImpl(user)
        var token =
                new UsernamePasswordAuthenticationToken(userDetailsImpl, "password", userDetailsImpl.authorities)
        SecurityContext context = SecurityContextHolder.getContext()
        context.setAuthentication (token)
        return context
    }
}
