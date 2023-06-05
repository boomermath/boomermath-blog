package com.boomermath.boomermathblog.data.dto.response;

import com.boomermath.boomermathblog.data.entities.BlogArticle;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BlogSearchResults {
    private int totalPages;
    private List<BlogArticle> articles;
}
