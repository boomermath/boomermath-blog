package com.boomermath.boomermathblog.web;

import com.boomermath.boomermathblog.data.dto.request.ArticleQueryData;
import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.repositories.BlogArticleRepository;
import com.boomermath.boomermathblog.data.specification.BlogArticleSpecification;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    public BlogArticle getArticle(@Argument String slug, DataFetchingEnvironment env) {
        log.info("GET ARTICLE");
        DataFetchingFieldSelectionSet selectionSet = env.getSelectionSet();

        BlogArticleSpecification articleQuery = BlogArticleSpecification.builder()
                .slug(slug)
                .queryAuthor(selectionSet.contains("author"))
                .queryTags(selectionSet.contains("tags"))
                .build();

        return blogArticleRepository.findOne(articleQuery).orElse(null);
    }

    @QueryMapping
    public Page<BlogArticle> searchArticles(@Argument ArticleQueryData query) {
        return null;
    }

    @QueryMapping
    public Page<BlogArticle> testQuery(@Argument String slug) {
        return null;
    }
}
