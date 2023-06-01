package com.boomermath.boomermathblog.web;

import com.boomermath.boomermathblog.data.dto.query.BlogSearchResults;
import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.repositories.BlogArticleRepository;
import com.boomermath.boomermathblog.data.values.ArticleStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class BlogArticleController {
    private final BlogArticleRepository blogArticleRepository;

    public BlogArticleController(BlogArticleRepository blogArticleRepository) {
        this.blogArticleRepository = blogArticleRepository;
    }

    @QueryMapping
    public BlogArticle getArticle(@Argument String slug) {
        return blogArticleRepository.findBlogArticleBySlugAndStatus(slug, ArticleStatus.PUBLISHED).orElse(null);
    }

    @QueryMapping
    public BlogSearchResults searchArticlesByQuery(@Argument String articleQuery, @Argument int pageNumber, @Argument int resultsPerPage) {
        Page<BlogArticle> blogArticlePages = blogArticleRepository
                .findBlogArticlesByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrContentContainingIgnoreCaseAndStatus(
                        articleQuery, articleQuery, articleQuery, ArticleStatus.PUBLISHED, PageRequest.of(pageNumber, resultsPerPage)
                );

        return BlogSearchResults.builder()
                .articles(blogArticlePages)
                .numberOfPages(blogArticlePages.getTotalPages())
                .build();
    }
}
