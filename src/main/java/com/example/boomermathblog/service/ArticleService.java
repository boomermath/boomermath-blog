package com.example.boomermathblog.service;

import com.example.boomermathblog.data.entities.BlogArticle;
import com.example.boomermathblog.data.entities.BlogTag;
import com.example.boomermathblog.data.entities.BlogUser;
import com.example.boomermathblog.data.repositories.BlogArticleRepository;
import com.example.boomermathblog.data.repositories.BlogUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleService {

    private final BlogUserRepository blogUserRepository;
    private final BlogArticleRepository blogArticleRepository;

    public ArticleService(BlogUserRepository blogUserRepository, BlogArticleRepository blogArticleRepository) {
        this.blogUserRepository = blogUserRepository;
        this.blogArticleRepository = blogArticleRepository;
    }

    public Page<BlogArticle> getRecommendedArticles(UUID userId) {
        BlogUser blogUser = blogUserRepository.findBlogUserById(userId);
        HashMap<BlogTag, Integer> blogArticlesWithTag = new HashMap<>();

        for (BlogArticle blogArticle : blogUser.getReadArticles()) {
            for (BlogTag blogTag : blogArticle.getTags()) {
                if (blogArticlesWithTag.containsKey(blogTag)) {
                    blogArticlesWithTag.put(blogTag, 1);
                } else {
                    blogArticlesWithTag.computeIfPresent(blogTag, (k, v) -> v + 1);
                }
            }
        }

        List<BlogTag> topBlogTags = blogArticlesWithTag.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .toList()
                .subList(0, 5);

        return blogArticleRepository.findBlogArticlesByTagsContaining(topBlogTags,
                PageRequest.of(0, 10, Sort.by(Sort.Order.asc("likes"))));
    }
}
