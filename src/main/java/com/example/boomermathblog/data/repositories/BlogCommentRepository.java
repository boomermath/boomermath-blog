package com.example.boomermathblog.data.repositories;

import com.example.boomermathblog.data.entities.BlogArticle;
import com.example.boomermathblog.data.entities.BlogComment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface BlogCommentRepository extends CrudRepository<BlogComment, UUID> {
    List<BlogComment> findBlogCommentsByArticle(BlogArticle blogArticle);
}
