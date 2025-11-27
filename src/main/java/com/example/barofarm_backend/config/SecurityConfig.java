package com.example.barofarm_backend.config;

import com.example.barofarm_backend.filter.JwtAuthenticationFilter;
import com.example.barofarm_backend.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // CORS 허용
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화 (JWT 사용 시)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 미사용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/login",
                                "/api/users/register", // 회원가입 경로
                                "/api/users/login",     // 로그인 경로
                                "/api/products",
                                "/api/products/**",
                                "/api/b2b/**",
                                "/api/wishlist",
                                "/api/reviews/product/**",
                                "/api/reviews/product/*/average",
                                "/api/reviews/upload/image",
                                "/uploads/**",

                                "/api/orders",              // 주문 생성
                                "/api/payments/**"          // 결제 승인 및 취소
                        ).permitAll()

                    
                        .requestMatchers("/api/orders", "/api/payments/**").permitAll() // 결제 흐름 전부 허용
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger 문서 허용
                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll() // CORS Preflight 허용

                        .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN") // 관리자 전용
                        .requestMatchers("/api/user/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") // 사용자
                        .requestMatchers("/api/farmers/**").hasAuthority("ROLE_FARMER") // 농부 전용


                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // CORS 상세 설정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:3001", "http://localhost:5173")); // 프론트 도메인
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
