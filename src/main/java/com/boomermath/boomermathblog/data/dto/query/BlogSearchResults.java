package com.boomermath.boomermathblog.data.dto.query;

import com.boomermath.boomermathblog.data.entities.BlogArticle;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public class BlogSearchResults {
    private int numberOfPages;
    private Page<BlogArticle> articles;
}
