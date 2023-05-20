package com.example.boomermathblog.data.web;


import com.example.boomermathblog.data.entities.BlogUser;
import com.example.boomermathblog.data.repositories.BlogUserRepository;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.security.auth.login.LoginException;
import java.util.Optional;

@Controller
public class BlogAuthController {

    private final BlogUserRepository blogUserRepository;
    private final PasswordEncoder passwordEncoder;

    public BlogAuthController(BlogUserRepository blogUserRepository, PasswordEncoder passwordEncoder) {
        this.blogUserRepository = blogUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @QueryMapping
    public BlogUser login(String nameOrEmail, String password) throws LoginException {
        Optional<BlogUser> retrievedUser = blogUserRepository.findBlogUserByUsernameOrEmail(nameOrEmail, nameOrEmail);

        if (retrievedUser.isEmpty()) {
            throw new LoginException("Invalid Login!");
        }

        BlogUser blogUser = retrievedUser.get();

        if (!passwordEncoder.matches(password, blogUser.getPassword())) {
            throw new LoginException("Invalid Login!");
        }

        return blogUser;
    }

    @QueryMapping
    public BlogUser signup(String username, String email, String password) throws LoginException {
        boolean userExists = blogUserRepository.findBlogUserByUsernameOrEmail(username, email).isPresent();

        if (userExists) {
            throw new LoginException("User already exists!");
        }

        BlogUser blogUser = BlogUser.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        return blogUserRepository.save(blogUser);
    }
}
