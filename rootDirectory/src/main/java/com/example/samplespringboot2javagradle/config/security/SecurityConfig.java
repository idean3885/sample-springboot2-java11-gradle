package com.example.samplespringboot2javagradle.config.security;

import com.example.samplespringboot2javagradle.consts.entity.MemberRole;
import com.example.samplespringboot2javagradle.filter.AuthenticationProcessingFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserDetailsService userDetailsService;
  private final ObjectMapper objectMapper;

  private static final String LOGIN = "/login";
  private static final String LOGIN_SUCCESS = "/";
  private static final String LOGOUT = "/logout";
  private static final String LOGOUT_SUCCESS = LOGIN;
  private static final String API_DOCS = "/api-docs**/**";

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.formLogin(
        configurer ->
            configurer
                //                                .loginPage(Security.LOGIN) // 로그인 페이지 구현?
                .successHandler(
                    ((request, response, authentication) -> {
                      var userDetails = (UserDetailsImpl) authentication.getPrincipal();
                      var remoteAddress =
                          Optional.ofNullable(authentication.getDetails())
                              .map(WebAuthenticationDetails.class::cast)
                              .map(WebAuthenticationDetails::getRemoteAddress)
                              .orElse(null);
                      response.sendRedirect(LOGIN_SUCCESS);
                    }))
                .failureUrl(LOGIN));
    httpSecurity.userDetailsService(userDetailsService);
    httpSecurity.csrf(AbstractHttpConfigurer::disable);
    httpSecurity.cors(configurer -> configurer.configurationSource(corsConfigurationSource()));
    httpSecurity.headers().frameOptions().sameOrigin();
    httpSecurity.authorizeHttpRequests(
        request ->
            request
                .antMatchers(HttpMethod.POST, LOGIN)
                .permitAll()
                .antMatchers(HttpMethod.GET, LOGIN)
                .permitAll()
                .antMatchers(HttpMethod.GET, API_DOCS)
                .hasRole(MemberRole.ROLE_ADMIN.getRoleName())
                .mvcMatchers(String.valueOf(PathRequest.toStaticResources().atCommonLocations()))
                .permitAll()
                .anyRequest()
                .authenticated());
    httpSecurity.logout(
        configurer ->
            configurer
                .logoutUrl(LOGOUT)
                .logoutSuccessHandler(
                    ((request, response, authentication) ->
                        response.sendRedirect(LOGOUT_SUCCESS))));
    httpSecurity.addFilterBefore(
        new AuthenticationProcessingFilter(objectMapper),
        UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    // h2-console 접근 허용 설정
    return web -> web.ignoring().requestMatchers(PathRequest.toH2Console());
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    var configuration = new CorsConfiguration();
    configuration.applyPermitDefaultValues();
    configuration.setAllowedOriginPatterns(List.of("*"));
    configuration.setAllowedMethods(List.of("*"));
    configuration.setAllowedHeaders(
        Arrays.asList(
            HttpHeaders.AUTHORIZATION,
            HttpHeaders.CONTENT_TYPE,
            HttpHeaders.CONTENT_LENGTH,
            HttpHeaders.ACCEPT_LANGUAGE));
    configuration.setMaxAge(1728000L);
    configuration.setAllowCredentials(true);
    var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
