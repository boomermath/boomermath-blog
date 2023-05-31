package com.boomermath.boomermathblog.web;

import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.repositories.BlogArticleRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class BlogArticleController {
    private final BlogArticleRepository blogArticleRepository;

    public BlogArticleController(BlogArticleRepository blogArticleRepository) {
        this.blogArticleRepository = blogArticleRepository;
    }

    @QueryMapping
    public BlogArticle findArticle(@Argument String slug) {
        return blogArticleRepository.findBlogArticleBySlug(slug).orElse(null);
    }
}
