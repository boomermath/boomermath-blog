package com.boomermath.boomermathblog.data.views;

import com.blazebit.persistence.integration.graphql.GraphQLName;
import com.blazebit.persistence.view.EntityView;
import com.boomermath.boomermathblog.data.entities.BlogComment;

import java.time.LocalDateTime;

@GraphQLName("BlogComment")
@EntityView(BlogComment.class)
public interface BlogCommentView {
    String getComment();

    BlogAuthorView getAuthor();

    LocalDateTime getCreatedTimestamp();
}
