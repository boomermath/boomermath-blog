package com.boomermath.boomermathblog.data.dto.response;

import com.boomermath.boomermathblog.data.entities.BlogArticle;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
public class BlogSearchResults {
    private int totalPages;
    private Page<BlogArticle> articles;
}
