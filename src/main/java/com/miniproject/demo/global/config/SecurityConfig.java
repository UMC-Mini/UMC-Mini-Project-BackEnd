package com.miniproject.demo.global.config;

import com.miniproject.demo.global.config.filter.*;
import com.miniproject.demo.global.config.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final PrincipalDetailsService principalDetailsService;
    private final JWTAccessDeniedHandler jwtAccessDeniedHandler;
    private final JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final String[] allowUrl = {
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/api-docs/**",
            "/api/v1/users/**",
            "/chat",
            "/ws"
    };

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
        // csrf disable
        http.csrf(AbstractHttpConfigurer::disable);
        // form 로그인 방식 disable
        http.formLogin(AbstractHttpConfigurer::disable);
        // http basic 인증 방식 disable
        http.httpBasic(AbstractHttpConfigurer::disable);
        // 세션 사용 안함
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

         http.cors(configurer -> configurer
                .configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOriginPatterns(Collections.singletonList("*"));
                    config.setAllowedMethods(Collections.singletonList("*"));
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setAllowCredentials(true);
                    config.setMaxAge(3600L);
                    return config;
                }));

        /*http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(HttpMethod.POST,"/api/v1/users/signup").permitAll()
                .requestMatchers(HttpMethod.POST,"/api/v1/users/login").permitAll()
                .requestMatchers(allowUrl).permitAll()
                .anyRequest().authenticated());*/

        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
                UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JWTFilter(jwtUtil, principalDetailsService),
                UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JWTExceptionFilter(), JWTFilter.class);

        // 경로별 인가 작업
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(allowUrl).permitAll()
                        .requestMatchers("/**").authenticated()
                        .anyRequest().permitAll()
                );

        http.exceptionHandling(
                (configurer ->
                        configurer
                                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                                .accessDeniedHandler(jwtAccessDeniedHandler)
                )
        );

        return http.build();
    }
}
