package com.example.boomermathblog.data.repositories;

import com.example.boomermathblog.data.entities.BlogArticle;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BlogArticleRepository extends CrudRepository<BlogArticle, UUID> {
}
