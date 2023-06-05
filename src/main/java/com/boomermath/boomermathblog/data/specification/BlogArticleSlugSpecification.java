package com.boomermath.boomermathblog.data.specification;

import com.boomermath.boomermathblog.data.entities.BlogArticle;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BlogArticleSlugSpecification extends JoinSpecification<BlogArticle>{
    private String slug;
    @Override
    public Predicate toPredicate(Root<BlogArticle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        super.toPredicate(root, query, criteriaBuilder);
        return criteriaBuilder.equal(root.get("slug"), slug);
    }
}
