package com.example.bigbrotherbe.global.common.config;

import com.example.bigbrotherbe.global.auth.jwt.filter.JwtAuthenticationFilter;
import com.example.bigbrotherbe.global.auth.jwt.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtTokenService jwtTokenService;
    public static final String SERVER = "/api/v1";
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // REST API이므로 basic auth 및 csrf 보안을 사용하지 않음
            .httpBasic(HttpBasicConfigurer::disable)
            .csrf(CsrfConfigurer::disable)
            // JWT를 사용하기 때문에 세션을 사용하지 않음
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                // 해당 API에 대해서는 모든 요청을 허가
                .requestMatchers(SERVER+"/members/sign-in").permitAll()
                .requestMatchers(SERVER+"/members/sign-up/**").permitAll()
                .requestMatchers(SERVER+"/members/refresh").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                // USER 권한이 있어야 요청할 수 있음
                .requestMatchers(SERVER+"/members/test").hasAnyRole("ADMIN","USER")
                .requestMatchers(SERVER+"/members").hasAnyRole("ADMIN","USER")
                .requestMatchers(SERVER+"/members/information").hasAnyRole("ADMIN","USER")
                // 유저 어드민 권한이 있어야 요청할 수 있음
                .requestMatchers(SERVER+"/members/password").permitAll()
                // 
                .requestMatchers(SERVER+"/members/manager").hasRole("ADMIN")
                // 이 밖에 모든 요청에 대해서 인증을 필요로 한다는 설정
                .anyRequest().permitAll()
            )
            // JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenService),
                UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt Encoder 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
