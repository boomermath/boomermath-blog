package com.boomermath.boomermathblog.data.dto.request;

import com.boomermath.boomermathblog.data.entities.BlogTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ArticleQueryData {
    private String query;
    private List<BlogTag> tags;
    @Getter
    @AllArgsConstructor
    public static class Tag {
        private String name;
    }
}
