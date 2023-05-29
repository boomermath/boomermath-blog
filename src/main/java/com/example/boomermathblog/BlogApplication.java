package com.example.boomermathblog;

import com.example.boomermathblog.data.entities.BlogArticle;
import com.example.boomermathblog.data.entities.BlogComment;
import com.example.boomermathblog.data.entities.BlogUser;
import com.example.boomermathblog.data.repositories.BlogArticleRepository;
import com.example.boomermathblog.data.repositories.BlogCommentRepository;
import com.example.boomermathblog.data.repositories.BlogUserRepository;
import com.example.boomermathblog.data.values.ArticleStatus;
import com.example.boomermathblog.data.values.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BlogCommentRepository blogCommentRepository, BlogUserRepository blogUserRepository, BlogArticleRepository blogArticleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Optional<BlogUser> optionalBlogUser = blogUserRepository.findBlogUserByUsername("boomermath");
            BlogUser blogUser;

            if (optionalBlogUser.isEmpty()) {
                BlogUser boomermath = BlogUser.builder()
                        .username("boomermath")
                        .role(UserRole.USER)
                        .email("boomermath@gmail.com")
                        .password(passwordEncoder.encode("DAONE364"))
                        .build();
                blogUser = blogUserRepository.save(boomermath);
            } else {
                return;
            }

            BlogArticle blogArticle1 = BlogArticle.builder()
                    .title("First")
                    .description("asdfasdfasdfasdfsdfasdfsafsdfasdff")
                    .content("veyr coolas dfjasdiofjasf")
                    .status(ArticleStatus.PUBLISHED)
                    .author(blogUser)
                    .build();

            blogArticle1 = blogArticleRepository.save(blogArticle1);

            BlogComment blogComment1 = BlogComment.builder()
                    .author(blogUser)
                    .article(blogArticle1)
                    .comment("funny comment here")
                    .build();

            blogCommentRepository.save(blogComment1);
        };
    }
}
