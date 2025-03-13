package com.example.samplespringboot2javagradle


import com.example.samplespringboot2javagradle.config.security.SecurityConfig
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Import

@TestConfiguration
@Import([TestUserDetailsServiceImpl, SecurityConfig, H2ConsoleProperties])
class TestConfig {
}
