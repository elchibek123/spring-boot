package com.example.demo.config.security;

import com.example.demo.config.jwt.JwtFilter;
import com.example.demo.config.jwt.JwtService;
import com.example.demo.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtService jwtService, UserRepository userRepository) throws Exception {
//        http.authorizeHttpRequests((request) -> {
//            request
//                    .requestMatchers(
//                            "/api/auth",
//                            "/api/auth/login"
//                    )
//                    .permitAll()
//                    .anyRequest()
//                    .hasAuthority("USER");
//        });
//        http.addFilterBefore(new JwtFilter(jwtService, userRepository), AuthenticationFilter.class);
//        http.cors(AbstractHttpConfigurer::disable);
//        http.csrf(AbstractHttpConfigurer::disable);
//        return http.build();
        return http.authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers(
                                    "/api/auth/*")
                            .permitAll()
                            .requestMatchers("/api/users")
                            .hasAuthority("ADMIN")
                            .anyRequest()
                            .authenticated();
                })

                .cors(cors -> {
                    cors.configurationSource(request -> {
                        var corsConfiguration = new CorsConfiguration();
                        corsConfiguration.addAllowedOrigin("*");
                        corsConfiguration.addAllowedMethod("*");
                        corsConfiguration.addAllowedHeader("*");
                        return corsConfiguration;
                    });
                })

                .csrf(AbstractHttpConfigurer::disable)

                .addFilterBefore(new JwtFilter(jwtService, userRepository), AuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
