package com.example.boomermathblog.data.repositories;

import com.example.boomermathblog.data.entities.BlogUserArticles;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BlogUserArticleRepository extends CrudRepository<BlogUserArticles, UUID> {
}
