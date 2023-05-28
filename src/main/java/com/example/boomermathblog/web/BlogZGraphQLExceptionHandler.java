package com.example.boomermathblog.web;

import com.example.boomermathblog.exception.custom.AuthException;
import com.example.boomermathblog.exception.custom.BlogCustomException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BlogZGraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(@NonNull Throwable ex, DataFetchingEnvironment env) {
        GraphqlErrorBuilder<?> graphqlErrorBuilder = GraphqlErrorBuilder.newError()
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation());

        GraphQLError graphQLError = null;

        if (ex instanceof BlogCustomException) {
            if (ex instanceof AuthException) {
                graphqlErrorBuilder.errorType(ErrorType.UNAUTHORIZED);
            } else {
                graphqlErrorBuilder.errorType(ErrorType.BAD_REQUEST);
            }

            graphQLError = graphqlErrorBuilder.message(ex.getMessage())
                    .build();
        } else if (ex instanceof AccessDeniedException) {
            graphQLError = graphqlErrorBuilder
                    .errorType(ErrorType.UNAUTHORIZED)
                    .message("UNAUTHORIZED")
                    .build();
        }

        return graphQLError;
    }
}
