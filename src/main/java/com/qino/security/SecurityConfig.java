//package com.qino.security;
//
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@Getter
//@EnableWebSecurity
//public abstract class SecurityConfig extends WebSecurityConfiguration {
//
//    private final CustomUserDetailService customUserDetailService;
//
//
//    @Autowired
//    public SecurityConfig(CustomUserDetailService customUserDetailService) {
//        this.customUserDetailService = customUserDetailService;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/api/auth/**").permitAll()
//                        .anyRequest().permitAll());
//
//        return http.build();
//    }
//}
