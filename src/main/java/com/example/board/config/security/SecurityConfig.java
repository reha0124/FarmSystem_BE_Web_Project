package com.example.board.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder를 Bean으로 등록하여 주입받아 사용
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (API 테스트용)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/crud/**").permitAll() // CRUD API 허용
                .requestMatchers("/actuator/**").permitAll() // Actuator 허용
                .anyRequest().authenticated() // 나머지는 인증 필요
            );

        return http.build();
    }
}
