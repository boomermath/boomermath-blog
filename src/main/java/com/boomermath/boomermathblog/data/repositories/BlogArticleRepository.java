package com.boomermath.boomermathblog.data.repositories;

import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.values.ArticleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlogArticleRepository extends PagingAndSortingRepository<BlogArticle, UUID>, CrudRepository<BlogArticle, UUID> {
    Optional<BlogArticle> findBlogArticleBySlugAndStatus(String slug, ArticleStatus status);

    @EntityGraph(attributePaths = "author")
    Optional<BlogArticle> findBlogArticleWithAuthorBySlugAndStatus(String slug, ArticleStatus status);
    
    Page<BlogArticle> findBlogArticlesByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrContentContainsIgnoreCaseAndStatus
            (String title, String description, String content, ArticleStatus status, Pageable pageable, EntityGraph entityGraph);
}
