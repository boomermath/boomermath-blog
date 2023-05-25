package com.example.boomermathblog.data.repositories;

import com.example.boomermathblog.data.entities.BlogArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlogArticleRepository extends JpaRepository<BlogArticle, UUID> {
    Optional<BlogArticle> findBlogArticleByTitle(String slug);
}
