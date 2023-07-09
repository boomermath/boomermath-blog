package com.boomermath.boomermathblog.data.views;

import com.blazebit.persistence.integration.graphql.GraphQLName;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.boomermath.boomermathblog.data.entities.BlogArticle;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@GraphQLName("BlogArticle")
@EntityView(BlogArticle.class)
public interface BlogArticleView {
    @IdMapping
    UUID getId();
    String getTitle();
    String getSlug();
    String getDescription();
    String getContent();
    Integer getLikes();
    Timestamp getCreatedDate();
    Timestamp getLastModifiedDate();

    List<BlogTagView> getTags();

    List<BlogCommentView> getComments();
}
