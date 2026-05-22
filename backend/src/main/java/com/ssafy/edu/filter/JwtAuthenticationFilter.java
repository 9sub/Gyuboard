package com.ssafy.edu.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.ssafy.edu.exception.ApiException;
import com.ssafy.edu.model.dto.MemberDto;
import com.ssafy.edu.model.service.MemberService;
import com.ssafy.edu.util.JwtUtil;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }

        return path.equals("/api/member/login")
                || path.equals("/api/member/join")
                || path.equals("/api/member/logout");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            	
                throw new ApiException(
                        HttpStatus.UNAUTHORIZED,
                        "UNAUTHORIZED",
                        "인증 토큰이 필요합니다."
                );
            }

            String token = authorizationHeader.substring(7);

            Claims claims = jwtUtil.parseToken(token);

            Integer userId = claims.get("userId", Integer.class);
            String writer = claims.get("writer", String.class);
            
            

            if (userId == null || writer == null) {
                throw new ApiException(
                        HttpStatus.UNAUTHORIZED,
                        "INVALID_TOKEN",
                        "유효하지 않은 토큰입니다."
                );
            }

            MemberDto user = memberService.findByWriter(writer);
//            log.info("user={}, userId={}, writer={}",user,  userId, writer);

            if (user == null || user.getUserId() != userId) {
                throw new ApiException(
                        HttpStatus.UNAUTHORIZED,
                        "INVALID_TOKEN",
                        "유효하지 않은 토큰입니다."
                );
            }

            request.setAttribute("loginUser", user);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            handlerExceptionResolver.resolveException(
                    request,
                    response,
                    null,
                    e
            );
        }
    }
}