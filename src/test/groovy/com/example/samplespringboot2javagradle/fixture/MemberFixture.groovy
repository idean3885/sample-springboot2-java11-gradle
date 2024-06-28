package com.example.samplespringboot2javagradle.fixture

import com.example.samplespringboot2javagradle.entity.member.Member

class MemberFixture {

    static Member userDetails(String username, String[] roles) {
        return new Member(
                id: 1L,
                email: username,
                roleList: roles
        )
    }
}
