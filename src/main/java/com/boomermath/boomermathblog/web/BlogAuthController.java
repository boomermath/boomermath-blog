package com.boomermath.boomermathblog.web;


import com.boomermath.boomermathblog.data.dto.graphql.JwtToken;
import com.boomermath.boomermathblog.data.dto.graphql.LoginData;
import com.boomermath.boomermathblog.data.dto.graphql.RegisterData;
import com.boomermath.boomermathblog.data.entities.BlogUser;
import com.boomermath.boomermathblog.data.repositories.BlogUserRepository;
import com.boomermath.boomermathblog.data.values.UserRole;
import com.boomermath.boomermathblog.exception.ExceptionBuilder;
import com.boomermath.boomermathblog.exception.message.AuthExceptions;
import com.boomermath.boomermathblog.web.security.jwt.BlogJwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

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

    private JwtToken generateTokenFromUser(BlogUser blogUser) {
        String userRole = blogUser.getRole().toString();
        String jwtToken = blogJwtUtils.generate(blogUser.getId(), userRole);

        return JwtToken
                .builder()
                .token(jwtToken)
                .build();
    }

    @QueryMapping
    public JwtToken login(@Argument LoginData loginData) throws AuthenticationException {
        String nameOrEmail = loginData.getNameOrEmail();
        Optional<BlogUser> retrievedUser = blogUserRepository.findBlogUserByUsernameOrEmail(nameOrEmail, nameOrEmail);

        if (retrievedUser.isEmpty()) {
            throw ExceptionBuilder.authException(AuthExceptions.INVALID_LOGIN);
        }

        BlogUser blogUser = retrievedUser.get();

        if (!passwordEncoder.matches(loginData.getPassword(), blogUser.getPassword())) {
            throw ExceptionBuilder.authException(AuthExceptions.INVALID_LOGIN);
        }

        return generateTokenFromUser(blogUser);
    }

    @QueryMapping
    public JwtToken register(@Argument RegisterData registerData) throws AuthenticationException {
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

        return generateTokenFromUser(blogUser);
    }
}
