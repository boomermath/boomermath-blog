package com.boomermath.boomermathblog.data.dto.response;

import com.boomermath.boomermathblog.data.entities.BlogComment;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
public class BlogCommentResults {
    private int nextPage;
    private List<BlogComment> comments;
}
