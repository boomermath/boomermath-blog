package com.boomermath.boomermathblog.data.specification;

import com.boomermath.boomermathblog.data.dto.request.ArticleQueryData;
import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.entities.BlogTag;
import com.boomermath.boomermathblog.data.values.ArticleStatus;
import jakarta.persistence.criteria.*;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Builder
public class BlogArticleSpecification implements Specification<BlogArticle> {

    private String slug;
    private ArticleQueryData articleQueryData;
    private boolean queryAuthor;
    private boolean queryTags;

    @Override
    public Predicate toPredicate(@NonNull Root<BlogArticle> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        if (queryAuthor) {
            root.fetch("author");
        }

        if (queryTags) {
            root.fetch("tags");
        }

        if (articleQueryData != null) {
            String articleQuery = articleQueryData.getQuery();

            if (articleQuery != null) {
                criteriaBuilder.and(
                        criteriaBuilder.equal(
                                criteriaBuilder.lower(
                                        root.get("title")
                                ), articleQuery.toLowerCase()
                        )
                );
            }

            List<BlogTag> blogTags = articleQueryData.getTags();

            if (blogTags != null) {
                Subquery<BlogTag> blogTagSubquery = query.subquery(BlogTag.class);
                Root<BlogTag> blogTagRoot = blogTagSubquery.from(BlogTag.class);

                CriteriaBuilder.In<BlogTag> blogTagIn = criteriaBuilder.in(blogTagRoot.get("name"));

                for (BlogTag blogTag : blogTags) {
                    blogTagIn.value(blogTag);
                }

                blogTagSubquery
                        .select(blogTagRoot)
                        .where(blogTagIn);

                criteriaBuilder.exists(blogTagSubquery);
            }
        } else if (slug != null) {
            criteriaBuilder.and(
                    criteriaBuilder.equal(root.get("slug"), slug)
            );
        } else {
            throw new IllegalStateException("Must specify slug or query data!");
        }

        return criteriaBuilder.and(
                criteriaBuilder.equal(root.get("status"), ArticleStatus.PUBLISHED)
        );
    }
}
