package com.boomermath.boomermathblog.web;

import com.boomermath.boomermathblog.data.dto.response.BlogCommentResults;
import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.entities.BlogComment;
import com.boomermath.boomermathblog.data.repositories.BlogCommentRepository;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class BlogCommentController {
    private final BlogCommentRepository blogCommentRepository;

    public BlogCommentController(BlogCommentRepository blogCommentRepository) {
        this.blogCommentRepository = blogCommentRepository;
    }

    @SchemaMapping(typeName = "BlogArticle", field = "comments")
    public BlogCommentResults comments(@Argument int count, @Argument int page, BlogArticle blogArticle, DataFetchingEnvironment env) {
        DataFetchingFieldSelectionSet selectionSet = env.getSelectionSet();
        Pageable pageable = PageRequest.of(page, count);

        Slice<BlogComment> blogComments;

        if (selectionSet.contains("comments/author")) {
            blogComments = blogCommentRepository.findBlogCommentsWithAuthorByArticle(blogArticle, pageable);
        } else {
            blogComments = blogCommentRepository.findBlogCommentsByArticle(blogArticle, pageable);
        }

        return BlogCommentResults.builder()
                .nextPage(blogComments.nextOrLastPageable().getPageNumber())
                .comments(blogComments.getContent())
                .build();
    }
}
