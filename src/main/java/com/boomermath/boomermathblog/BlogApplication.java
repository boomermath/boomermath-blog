package com.boomermath.boomermathblog;

import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.entities.BlogComment;
import com.boomermath.boomermathblog.data.entities.BlogTag;
import com.boomermath.boomermathblog.data.entities.BlogUser;
import com.boomermath.boomermathblog.data.repositories.BlogArticleRepository;
import com.boomermath.boomermathblog.data.repositories.BlogCommentRepository;
import com.boomermath.boomermathblog.data.repositories.BlogTagRepository;
import com.boomermath.boomermathblog.data.repositories.BlogUserRepository;
import com.boomermath.boomermathblog.data.values.ArticleStatus;
import com.boomermath.boomermathblog.data.values.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner commandLineRunner(BlogCommentRepository blogCommentRepository,
                                               BlogUserRepository blogUserRepository,
                                               BlogTagRepository blogTagRepository,
                                               BlogArticleRepository blogArticleRepository,
                                               PasswordEncoder passwordEncoder) {
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
            blogUser.addReadArticle(blogArticle1);
            blogArticle1 = blogArticleRepository.save(blogArticle1);
            log.info(blogArticle1.getSlug());
            BlogComment blogComment1 = BlogComment.builder()
                    .author(blogUser)
                    .article(blogArticle1)
                    .comment("funny comment here")
                    .build();

            BlogTag blogTag = BlogTag.builder().name("vue.js").build();
            blogTag.addArticle(blogArticle1);

            blogCommentRepository.save(blogComment1);
            blogTagRepository.save(blogTag);
        };
    }

//    @Bean
//    @Transactional
//    public CommandLineRunner commandLineRunner2(BlogUserRepository blogUserRepository, BlogArticleRepository blogArticleRepository, BlogCommentRepository blogCommentRepository) {
//        return args -> {
//            Optional<BlogUser> optionalBlogUser = blogUserRepository.findBlogUserByUsername("boomermath");
//            Optional<BlogArticle> optionalBlogArticle = blogArticleRepository.findBlogArticleBySlugAndStatus("first-2042418473", ArticleStatus.PUBLISHED);
//
//            if (optionalBlogArticle.isPresent() && optionalBlogUser.isPresent()) {
//                BlogUser user = optionalBlogUser.get();
//                BlogArticle article = optionalBlogArticle.get();
//
//                List<BlogComment> blogComments = new ArrayList<>();
//
//                for (int i = 0; i < 10000; i++) {
//                    BlogComment comment = BlogComment.builder()
//                            .comment("Comment number: " + i)
//                            .author(user)
//                            .article(article)
//                            .build();
//
//                    blogComments.add(comment);
//                }
//
//                blogCommentRepository.saveAll(blogComments);
//            }
//        };
//    }
}
