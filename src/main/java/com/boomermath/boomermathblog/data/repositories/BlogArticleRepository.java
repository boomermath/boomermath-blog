package com.boomermath.boomermathblog.data.repositories;

import com.boomermath.boomermathblog.data.entities.BlogArticle;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface BlogArticleRepository extends PagingAndSortingRepository<BlogArticle, UUID>, CrudRepository<BlogArticle, UUID>, QuerydslPredicateExecutor<BlogArticle> {
    @EntityGraph(attributePaths = "tags")
    BlogArticle findBlogArticleBySlug(String slug);
}
