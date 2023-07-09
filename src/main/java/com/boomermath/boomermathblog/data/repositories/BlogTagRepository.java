package com.boomermath.boomermathblog.data.repositories;

import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.entities.BlogTag;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BlogTagRepository extends CrudRepository<BlogTag, UUID>, QuerydslPredicateExecutor<BlogArticle> {
    BlogTag findFirstByNameContaining(String name);
}
