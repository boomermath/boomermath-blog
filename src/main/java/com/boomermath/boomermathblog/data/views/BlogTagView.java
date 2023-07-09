package com.boomermath.boomermathblog.data.views;

import com.blazebit.persistence.integration.graphql.GraphQLName;
import com.blazebit.persistence.view.EntityView;
import com.boomermath.boomermathblog.data.entities.BlogTag;

@GraphQLName("BlogTag")
@EntityView(BlogTag.class)
public interface BlogTagView {
    String getName();
}
