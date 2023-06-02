package com.boomermath.boomermathblog.data.specification;

import org.springframework.data.jpa.domain.Specification;

import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.entities.BlogComment;
import com.boomermath.boomermathblog.data.entities.BlogTag;
import com.boomermath.boomermathblog.data.entities.BlogUser;
import com.boomermath.boomermathblog.data.values.ArticleStatus;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Join;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BlogArticleSpecification implements Specification<BlogArticle> {

    private String slug;
    @Builder.Default
    private boolean withAuthor = false;
    @Builder.Default
    private boolean withTags = false;
    @Builder.Default
    private boolean withComments = false;
    @Builder.Default
    private boolean withCommentUsers = false;

    public Predicate toPredicate(Root<BlogArticle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (withAuthor) {
            Join<BlogArticle, BlogUser> blogAuthorJoin = root.join("author");
            criteriaBuilder.and(blogAuthorJoin.getOn());
        }

        if (withTags) {
            Join<BlogArticle,BlogTag> blogTagJoin = root.join("tags");
            criteriaBuilder.and(blogTagJoin.getOn());
        } 

        if (withComments) {
            Join<BlogArticle, BlogComment> blogCommentJoin = root.join("comments");
            
            if (withCommentUsers) {
                Join<BlogComment, BlogUser> blogCommentUserJoin = blogCommentJoin.join("author");
                criteriaBuilder.and(blogCommentUserJoin.getOn());
            }

            criteriaBuilder.and(blogCommentJoin.getOn());
        }

        return criteriaBuilder
        .and(
            criteriaBuilder.equal(root.get("slug"), slug),
            criteriaBuilder.equal(root.get("status"), ArticleStatus.PUBLISHED)
        );
    }
}