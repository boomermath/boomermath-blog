package com.example.boomermathblog;

import com.example.boomermathblog.data.entities.BlogArticle;
import com.example.boomermathblog.data.entities.BlogComment;
import com.example.boomermathblog.data.entities.BlogUser;
import com.example.boomermathblog.data.entities.BlogUserArticles;
import com.example.boomermathblog.data.repositories.BlogArticleRepository;
import com.example.boomermathblog.data.repositories.BlogCommentRepository;
import com.example.boomermathblog.data.repositories.BlogUserArticleRepository;
import com.example.boomermathblog.data.repositories.BlogUserRepository;
import com.example.boomermathblog.data.values.ArticlesRoles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(BlogCommentRepository blogCommentRepository, BlogUserRepository blogUserRepository, BlogUserArticleRepository blogUserArticleRepository, BlogArticleRepository blogArticleRepository) {
        return args -> {
            BlogUser blogUser = BlogUser.builder()
                    .email("boomermath@gmail.com")
                    .username("boomermath")
                    .password("encryptedstuffig")
                    .build();

            BlogArticle blogArticle = BlogArticle.builder()
                    .title("This is a cool")
                    .description("This is a description for an article abt java")
                    .content("DJSFIOJDSFIOJDSOFIJSDFOIDSJFOIDSFJSDIOFJDSF")
                    .build();

            BlogUserArticles blogUserArticles = BlogUserArticles.builder()
                    .blogUser(blogUser)
                    .blogArticle(blogArticle)
                    .articleRole(ArticlesRoles.OWNER)
                    .build();

            blogUserRepository.save(blogUser);
            blogArticleRepository.save(blogArticle);
            blogUserArticleRepository.save(blogUserArticles);

            Optional<BlogUser> optUser = blogUserRepository.findBlogUserByUsername("boomermath");

            if (optUser.isPresent()) {
                BlogUser blogUserGot = optUser.get();
                BlogUserArticles blogUserArticle = blogUserGot.getBlogUserArticles().get(0);
                BlogArticle blogArticleGot = blogUserArticle.getBlogArticle();

                BlogComment blogComment = BlogComment.builder()
                        .blogUser(blogUserGot)
                        .blogArticle(blogArticleGot)
                        .comment("This is a really cool article!")
                        .build();

                blogArticleGot.setBlogComments(List.of(blogComment));
                blogCommentRepository.save(blogComment);
            }
        };
    }
}
