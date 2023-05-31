package com.boomermath.boomermathblog.web;

import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.repositories.BlogArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@Slf4j
public class BlogHomeController {
    private final BlogArticleRepository blogArticleRepository;

    public BlogHomeController(BlogArticleRepository blogArticleRepository) {
        this.blogArticleRepository = blogArticleRepository;
    }

    @QueryMapping
    public Page<BlogArticle> popularArticles() {
        return blogArticleRepository.findAll(
                PageRequest.of(0, 2, Sort.by(Sort.Order.asc("likes")))
        );
    }
}
