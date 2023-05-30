package com.example.boomermathblog.data.repositories;

import com.example.boomermathblog.data.entities.BlogArticle;
import com.example.boomermathblog.data.entities.BlogTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BlogArticleRepository extends PagingAndSortingRepository<BlogArticle, UUID>, CrudRepository<BlogArticle, UUID> {
    Page<BlogArticle> findBlogArticlesByTagsContaining(List<BlogTag> tags, Pageable pageable);
}
