package com.boomermath.boomermathblog.data.repositories;

import com.boomermath.boomermathblog.data.entities.BlogArticle;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface BlogArticleRepository extends PagingAndSortingRepository<BlogArticle, UUID>, CrudRepository<BlogArticle, UUID>, JpaSpecificationExecutor<BlogArticle> {
}
