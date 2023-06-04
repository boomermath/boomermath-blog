package com.boomermath.boomermathblog.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleQueryData {
    private String query;
    private Tag tags;
    @Getter
    @AllArgsConstructor
    public static class Tag {
        private String name;
    }
}
