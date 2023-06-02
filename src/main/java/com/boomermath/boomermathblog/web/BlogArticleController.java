package com.boomermath.boomermathblog.web;

import com.boomermath.boomermathblog.data.dto.query.BlogSearchResults;
import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.repositories.BlogArticleRepository;
import com.boomermath.boomermathblog.data.specification.BlogArticleSpecification;
import com.boomermath.boomermathblog.data.values.ArticleStatus;

import graphql.language.SelectionSet;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        log.info("Running fetcher");
        BlogArticleSpecification.BlogArticleSpecificationBuilder queryBuilder = BlogArticleSpecification.builder()
                                            .slug(slug);
        
        DataFetchingFieldSelectionSet selectionSet = env.getSelectionSet();
        
        if (selectionSet.contains("author")) {
            queryBuilder.withAuthor(true);
        }

        if (selectionSet.contains("tags")) {
            queryBuilder.withTags(true);
        }

        if (selectionSet.contains("comments")) {
            queryBuilder.withComments(true);
            
            DataFetchingFieldSelectionSet commentSet = selectionSet
            .getFields("comments")
            .get(0).getSelectionSet();

            if (commentSet.contains("author")) {
                queryBuilder.withCommentUsers(true);
            }
        }

        Optional<BlogArticle> blogArticle = blogArticleRepository.findOne(queryBuilder.build());

        if (blogArticle.isPresent()) {
            return blogArticle.get();
        }

        return null;
    }
}
