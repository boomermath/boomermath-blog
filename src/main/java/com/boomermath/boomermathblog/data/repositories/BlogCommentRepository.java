package com.boomermath.boomermathblog.data.repositories;

import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.entities.BlogComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlogCommentRepository extends PagingAndSortingRepository<BlogComment, UUID>, CrudRepository<BlogComment, UUID> {
    Slice<BlogComment> findBlogCommentsByArticle(BlogArticle article, Pageable pageable);

    @EntityGraph(attributePaths = "author")
    Slice<BlogComment> findBlogCommentsWithAuthorByArticle(BlogArticle article, Pageable pageable);
}
