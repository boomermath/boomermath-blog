package com.example.boomermathblog.web;

import com.example.boomermathblog.data.entities.BlogArticle;
import com.example.boomermathblog.data.entities.BlogTag;
import com.example.boomermathblog.data.repositories.BlogArticleRepository;
import com.example.boomermathblog.data.repositories.BlogTagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
public class BlogHomeController {
    private final BlogArticleRepository blogArticleRepository;
    private final BlogTagRepository blogTagRepository;

    public BlogHomeController(BlogArticleRepository blogArticleRepository, BlogTagRepository blogTagRepository) {
        this.blogArticleRepository = blogArticleRepository;
        this.blogTagRepository = blogTagRepository;
    }

    @QueryMapping
    public Page<BlogArticle> popularArticles() {
        return blogArticleRepository.findAll(
                PageRequest.of(0, 10, Sort.by(Sort.Order.desc("likes")))
        );
    }

    @QueryMapping
    public List<BlogArticle> tagArticles(@Argument String tag) {
        Optional<BlogTag> optionalBlogTag = blogTagRepository.findBlogTagByDescriptionContaining(tag);

        if (optionalBlogTag.isPresent()) {
            BlogTag blogTag = optionalBlogTag.get();
            return blogTag.getArticles();
        }

        return null;
    }
}
