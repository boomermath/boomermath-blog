package com.example.boomermathblog.web;


import com.example.boomermathblog.data.dto.JwtResponse;
import com.example.boomermathblog.data.dto.LoginData;
import com.example.boomermathblog.data.dto.RegisterData;
import com.example.boomermathblog.data.entities.BlogUser;
import com.example.boomermathblog.data.repositories.BlogUserRepository;
import com.example.boomermathblog.data.values.UserRole;
import com.example.boomermathblog.exception.ExceptionBuilder;
import com.example.boomermathblog.exception.codes.AuthExceptions;
import com.example.boomermathblog.web.security.jwt.BlogJwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.Collections;
import java.util.Optional;

@Controller
@Slf4j
public class BlogAuthController {

    private final BlogUserRepository blogUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final BlogJwtUtils blogJwtUtils;

    public BlogAuthController(BlogUserRepository blogUserRepository, PasswordEncoder passwordEncoder, BlogJwtUtils blogJwtUtils) {
        this.blogUserRepository = blogUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.blogJwtUtils = blogJwtUtils;
    }

    private JwtResponse securityContextFromUser(BlogUser blogUser) {
        String userRole = blogUser.getRole().toString();

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                blogUser.getId(), blogUser.getPassword(),
                Collections.singletonList(() -> userRole)
        );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        String jwtToken = blogJwtUtils.generate(blogUser.getId(), userRole);

        return JwtResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    @QueryMapping
    public JwtResponse login(@Argument LoginData loginData) throws AuthenticationException {
        String nameOrEmail = loginData.getNameOrEmail();
        Optional<BlogUser> retrievedUser = blogUserRepository.findBlogUserByUsernameOrEmail(nameOrEmail, nameOrEmail);

        if (retrievedUser.isEmpty()) {
            throw ExceptionBuilder.authException(AuthExceptions.INVALID_LOGIN);
        }

        BlogUser blogUser = retrievedUser.get();

        if (!passwordEncoder.matches(loginData.getPassword(), blogUser.getPassword())) {
            throw ExceptionBuilder.authException(AuthExceptions.INVALID_LOGIN);
        }

        return securityContextFromUser(blogUser);
    }

    @QueryMapping
    public JwtResponse register(@Argument RegisterData registerData) throws AuthenticationException {
        String username = registerData.getUsername();
        String email = registerData.getEmail();

        boolean userExists = blogUserRepository.findBlogUserByUsernameOrEmail(username, email).isPresent();

        if (userExists) {
            throw ExceptionBuilder.authException(AuthExceptions.USER_EXISTS);
        }

        BlogUser blogUser = BlogUser.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(registerData.getPassword()))
                .role(UserRole.USER)
                .build();

        blogUser = blogUserRepository.save(blogUser);
        log.info("Blog user info: ");
        log.info(blogUser.toString());
        return securityContextFromUser(blogUser);
    }
}
