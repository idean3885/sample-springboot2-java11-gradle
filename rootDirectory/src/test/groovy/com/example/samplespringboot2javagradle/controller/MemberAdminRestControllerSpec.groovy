package com.example.samplespringboot2javagradle.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import com.example.samplespringboot2javagradle.TestConfig
import com.example.samplespringboot2javagradle.WithCustomMockUser
import com.example.samplespringboot2javagradle.consts.entity.MemberRole
import com.example.samplespringboot2javagradle.controller.member.MemberAdminRestController
import com.example.samplespringboot2javagradle.dto.member.MemberAdminRspDto
import com.example.samplespringboot2javagradle.service.member.MemberAdminService
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@ActiveProfiles("test")
@WebMvcTest(MemberAdminRestController)
@Import([TestConfig])
class MemberAdminRestControllerSpec extends Specification {

    @Autowired
    MockMvc mvc

    @Autowired
    ObjectMapper objectMapper

    @WithCustomMockUser(username = "admin@email.com", roleList = [MemberRole.ROLE_ADMIN])
    def "findMember() 성공"() {
        given:
        def id = 1L
        def response = MemberAdminRspDto.builder()
                .id(id)
                .build()
        memberAdminService.get(_) >> response

        when:
        def contentAsString = mvc.perform(get("/api/members/v1/admin/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString
        def got = objectMapper.readValue(contentAsString, MemberAdminRspDto)

        then:
        // 객체 간 비교이기 때문에 EqualsAndHashCode 를 구현해야 한다.
        got == response
    }

    @SpringBean
    MemberAdminService memberAdminService = Mock()

    @WithCustomMockUser(username = "user@email.com", roleList = [MemberRole.ROLE_USER])
    def "findMember() 실패 - 권한 없음"() {
        given:
        def id = 1L

        when:
        def contentAsString = mvc.perform(get("/api/members/v1/admin/{id}", id)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden())
            .andReturn()
            .response
            .contentAsString

        then:
        contentAsString == ""
    }
}
