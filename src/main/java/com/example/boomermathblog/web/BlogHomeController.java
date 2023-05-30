package com.example.boomermathblog.web;

import com.example.boomermathblog.data.entities.BlogArticle;
import com.example.boomermathblog.data.repositories.BlogArticleRepository;
import com.example.boomermathblog.data.repositories.BlogTagRepository;
import com.example.boomermathblog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@Slf4j
public class BlogHomeController {
    private final BlogArticleRepository blogArticleRepository;
    private final BlogTagRepository blogTagRepository;
    private final ArticleService articleService;

    public BlogHomeController(BlogArticleRepository blogArticleRepository, BlogTagRepository blogTagRepository, ArticleService articleService) {
        this.blogArticleRepository = blogArticleRepository;
        this.blogTagRepository = blogTagRepository;
        this.articleService = articleService;
    }

    @QueryMapping
    public Page<BlogArticle> popularArticles() {
        return blogArticleRepository.findAll(
                PageRequest.of(0, 2, Sort.by(Sort.Order.asc("likes")))
        );
    }

    @QueryMapping
    public Page<BlogArticle> recommendedArticles(@AuthenticationPrincipal UUID userId) {
        return articleService.getRecommendedArticles(userId);
    }
}
