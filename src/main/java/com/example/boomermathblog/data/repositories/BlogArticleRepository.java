package com.example.boomermathblog.data.repositories;

import com.example.boomermathblog.data.entities.BlogArticle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlogArticleRepository extends PagingAndSortingRepository<BlogArticle, UUID>, CrudRepository<BlogArticle, UUID> {
}
