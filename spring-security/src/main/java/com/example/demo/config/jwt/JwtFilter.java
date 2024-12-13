package com.example.demo.config.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.config.security.AuthUser;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final String PREFIX_TOKEN = "Bearer ";

    public JwtFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        try {
            if (!isValidAuthToken(token)) {
                filterChain.doFilter(request, response);
                return;
            }
            String extractToken = extractToken(token);
            DecodedJWT decodedJWT = jwtService.validateToken(extractToken);
            Long userId = decodedJWT.getClaim("id").asLong();
            User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found!"));

            SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
            emptyContext.setAuthentication(new AuthUser(user));
            SecurityContextHolder.setContext(emptyContext);
        } catch (JWTVerificationException | NotFoundException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private boolean isValidAuthToken(String token) {
        return token != null && token.startsWith(PREFIX_TOKEN);
    }

    private String extractToken(String token) {
        return token.substring(PREFIX_TOKEN.length());
    }
}
