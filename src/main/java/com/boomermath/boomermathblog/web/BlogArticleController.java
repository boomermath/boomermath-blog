package com.boomermath.boomermathblog.web;

import com.boomermath.boomermathblog.data.dto.request.ArticleQueryData;
import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.repositories.BlogArticleRepository;
import com.boomermath.boomermathblog.data.values.ArticleStatus;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@Slf4j
public class BlogArticleController {
    private final BlogArticleRepository blogArticleRepository;

    public BlogArticleController(BlogArticleRepository blogArticleRepository) {
        this.blogArticleRepository = blogArticleRepository;
    }

    @QueryMapping
    public BlogArticle getArticle(@Argument String slug, DataFetchingEnvironment env) {
        DataFetchingFieldSelectionSet selectionSet = env.getSelectionSet();

        Optional<BlogArticle> optionalBlogArticle;

        if (selectionSet.contains("author")) {
            optionalBlogArticle = blogArticleRepository.findBlogArticleWithAuthorBySlugAndStatus(slug, ArticleStatus.PUBLISHED);
        } else {
            optionalBlogArticle = blogArticleRepository.findBlogArticleBySlugAndStatus(slug, ArticleStatus.PUBLISHED);
        }

        return optionalBlogArticle.orElse(null);
    }

    @QueryMapping
    public Page<BlogArticle> searchArticles(@Argument ArticleQueryData query) {
        return null;
    }
}
