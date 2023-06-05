package com.boomermath.boomermathblog.data.specification;

import com.boomermath.boomermathblog.data.dto.request.ArticleQueryData;
import com.boomermath.boomermathblog.data.entities.BlogArticle;
import com.boomermath.boomermathblog.data.entities.BlogTag;
import jakarta.persistence.criteria.*;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

@SuperBuilder
public class BlogArticleQuerySpecification extends JoinSpecification<BlogArticle> {

    private ArticleQueryData articleData;
    private boolean author;
    private boolean tags;

    @Override
    public Predicate toPredicate(@NonNull Root<BlogArticle> root, @NonNull CriteriaQuery<?> query, @NonNull CriteriaBuilder criteriaBuilder) {
        super.toPredicate(root, query, criteriaBuilder);

        Optional<String> possibleQuery = articleData.getQuery();
        Optional<List<BlogTag>> possibleTags = articleData.getBlogTags();

        if (possibleQuery.isPresent()) {
            String articleQuery = possibleQuery.get();
            String containsQuery = String.format("%%%s%%", articleQuery);

            query.where(
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), containsQuery),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), containsQuery),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), containsQuery)
                    )
            );
        }

        if (possibleTags.isPresent()) {
            List<BlogTag> blogTags = possibleTags.get();

            Subquery<BlogTag> subquery = query.subquery(BlogTag.class);
            Root<BlogTag> blogTagRoot = subquery.from(BlogTag.class);

            CriteriaBuilder.In<BlogTag> blogTagIn = criteriaBuilder.in(blogTagRoot.get("name"));

            for (BlogTag blogTag : blogTags) {
                blogTagIn.value(blogTag);
            }

            subquery
                    .select(blogTagRoot)
                    .where(blogTagIn);

            criteriaBuilder.and(
                    criteriaBuilder.exists(subquery)
            );
        }

        return criteriaBuilder.conjunction();
    }
}
