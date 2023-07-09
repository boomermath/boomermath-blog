package com.boomermath.boomermathblog.data.views;

import com.blazebit.persistence.integration.graphql.GraphQLName;
import com.blazebit.persistence.view.EntityView;
import com.boomermath.boomermathblog.data.entities.BlogUser;

@GraphQLName("BlogAuthor")
@EntityView(BlogUser.class)
public interface BlogAuthorView {
    String getUsername();
}
