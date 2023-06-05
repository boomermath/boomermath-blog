package com.boomermath.boomermathblog.data.specification;


import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@SuperBuilder
public class JoinSpecification<T> implements Specification<T> {

    @Singular
    private List<String> fields;
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        for (String field : fields) {
            root.fetch(field);
        }

        return criteriaBuilder.conjunction();
    }
}
