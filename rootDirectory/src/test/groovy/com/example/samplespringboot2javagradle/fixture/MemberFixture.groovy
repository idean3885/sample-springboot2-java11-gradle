package com.example.samplespringboot2javagradle.fixture

import com.example.samplespringboot2javagradle.consts.entity.MemberRole
import com.example.samplespringboot2javagradle.entity.member.Member

class MemberFixture {

    static Member userDetails(String username, MemberRole[] roles) {
        return new Member(
                id: 1L,
                email: username,
                roleList: roles
        )
    }

    static Member adminMember() {
        return new Member(
                id: 2L,
                email: 'admin@email.com',
                roleList: [MemberRole.ROLE_ADMIN]
        )
    }

    static Member userMember() {
        return new Member(
                id: 3L,
                email: 'user@email.com',
                roleList: [MemberRole.ROLE_USER]
        )
    }
}
