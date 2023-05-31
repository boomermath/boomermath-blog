package com.boomermath.boomermathblog.web.security.jwt;


import com.boomermath.boomermathblog.data.dto.JwtPayload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;


@Component
@Slf4j
public class BlogJwtSecurityFilter extends OncePerRequestFilter {

    private final BlogJwtUtils blogJwtUtils;

    public BlogJwtSecurityFilter(BlogJwtUtils blogJwtUtils) {
        this.blogJwtUtils = blogJwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header != null && header.startsWith(BlogJwtUtils.HEADER_PREFIX)) {
            JwtPayload jwtPayload;

            try {
                jwtPayload = blogJwtUtils.decode(header.substring(BlogJwtUtils.HEADER_PREFIX.length()));
            } catch (Exception e) {
                filterChain.doFilter(request, response);
                return;
            }

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    jwtPayload.getId(), null, Collections.singletonList(jwtPayload.getRole())
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }
}
