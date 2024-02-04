package com.ons.securitylayerJwt.security;

import com.ons.securitylayerJwt.Exceptions.Exceptions.JwtExpiration;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRefreshTokenFilter extends OncePerRequestFilter {
    private  final JwtUtilities jwtUtilities ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String refresh_token = request.getParameter("refresh_token");
        System.out.println(refresh_token);
        if (request.getServletPath().equals("/user/refresh-token") && jwtUtilities.validateRefreshToken(refresh_token)) {
            String username = jwtUtilities.extractUsername(refresh_token,true);
            List roles = jwtUtilities.extractClaimRefreshToken(refresh_token, claims -> claims.get("role", List.class));
            String newAccessToken = jwtUtilities.generateToken(username, roles);
            String newRefreshToken = jwtUtilities.generateRefreshToken(username,roles);
            response.setContentType("application/json");
            response.getWriter().write(String.format("{\"accessToken\": \"%s\", \"refreshToken\": \"%s\"}", newAccessToken, newRefreshToken));
        }else{
            filterChain.doFilter(request, response);
        }
    }
}
