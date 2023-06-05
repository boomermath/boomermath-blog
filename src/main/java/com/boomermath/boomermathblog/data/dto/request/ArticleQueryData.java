package com.boomermath.boomermathblog.data.dto.request;

import com.boomermath.boomermathblog.data.entities.BlogTag;
import org.springframework.data.web.ProjectedPayload;

import java.util.List;
import java.util.Optional;

@ProjectedPayload
public interface ArticleQueryData {
    Optional<String> getQuery();
    Optional<List<BlogTag>> getBlogTags();
}
