package com.boomermath.boomermathblog.web;

import com.boomermath.boomermathblog.data.dto.request.ArticleQueryData;
import com.boomermath.boomermathblog.data.dto.response.BlogSearchResults;
import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.repositories.BlogArticleRepository;
import com.boomermath.boomermathblog.data.specification.BlogArticleQuerySpecification;
import com.boomermath.boomermathblog.data.specification.BlogArticleSlugSpecification;
import com.boomermath.boomermathblog.data.specification.JoinSpecification;
import com.boomermath.boomermathblog.data.values.ArticleStatus;
import com.boomermath.boomermathblog.exception.AuthException;
import com.boomermath.boomermathblog.exception.message.AuthExceptions;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.UUID;

@Controller
@Slf4j
public class BlogArticleController {
    private final BlogArticleRepository blogArticleRepository;

    public BlogArticleController(BlogArticleRepository blogArticleRepository) {
        this.blogArticleRepository = blogArticleRepository;
    }

    private void doFetchJoin(JoinSpecification.JoinSpecificationBuilder<?, ?, ?> joinSpecBuilder, DataFetchingFieldSelectionSet selectionSet, String authorField, String tagField) {
        if (selectionSet.contains(authorField)) {
            joinSpecBuilder.field("author");
        }

        if (selectionSet.contains(tagField)) {
            joinSpecBuilder.field("tags");
        }
    }

    @QueryMapping
    public BlogArticle getArticle(@Argument String slug, @AuthenticationPrincipal UUID userId, DataFetchingEnvironment env) {
        BlogArticleSlugSpecification.BlogArticleSlugSpecificationBuilder<?, ?> blogArticleSpec = BlogArticleSlugSpecification.builder()
                .slug(slug);

        doFetchJoin(blogArticleSpec, env.getSelectionSet(), "author", "tags");

        Optional<BlogArticle> blogArticleOptional = blogArticleRepository.findOne(blogArticleSpec.build());

        if (blogArticleOptional.isEmpty()) return null;

        BlogArticle blogArticle = blogArticleOptional.get();

        if (blogArticle.getStatus().equals(ArticleStatus.DRAFT)) {
            boolean isAuthor = blogArticle.getAuthor().getId().equals(userId);
            boolean isEditor = blogArticle.getEditors()
                    .parallelStream()
                    .anyMatch(blogUser -> blogUser.getId().equals(userId));

            if (!isAuthor || !isEditor) {
                throw new AuthException(AuthExceptions.PRIVATE_ARTICLE);
            }
        }

        return blogArticle;
    }

    @QueryMapping
    public BlogSearchResults searchArticles(@Argument ArticleQueryData articleQuery, @Argument int count, @Argument int page, DataFetchingEnvironment env) {
        BlogArticleQuerySpecification.BlogArticleQuerySpecificationBuilder<?, ?> blogArticleSpec = BlogArticleQuerySpecification.builder()
                .articleData(articleQuery);
        Pageable pageable = PageRequest.of(page, count);

        doFetchJoin(blogArticleSpec, env.getSelectionSet(), "articles/author", "articles/tags");

        Page<BlogArticle> blogArticlePage = blogArticleRepository.findAll(blogArticleSpec.build(), pageable);

        return BlogSearchResults.builder()
                .totalPages(blogArticlePage.getTotalPages())
                .articles(blogArticlePage.getContent())
                .build();
    }

    @QueryMapping
    public Page<BlogArticle> testQuery(@Argument String slug) {
        return null;
    }
}
