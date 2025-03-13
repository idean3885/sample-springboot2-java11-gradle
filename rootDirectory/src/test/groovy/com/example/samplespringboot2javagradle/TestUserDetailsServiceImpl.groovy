package com.example.samplespringboot2javagradle

import com.example.samplespringboot2javagradle.config.security.UserDetailsImpl
import com.example.samplespringboot2javagradle.consts.entity.MemberRole
import com.example.samplespringboot2javagradle.fixture.MemberFixture
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class TestUserDetailsServiceImpl implements UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        def member
        if (usename == MemberFixture.adminMember().email) {
            member = MemberFixture.adminMember()
        } else if (username == MemberFixture.userMember().email) {
            member = MemberFixture.userMember()
        } else {
            member = MemberFixture.userDetails(username, _ as MemberRole)
        }
        return new UserDetailsImpl(member)
    }
}
