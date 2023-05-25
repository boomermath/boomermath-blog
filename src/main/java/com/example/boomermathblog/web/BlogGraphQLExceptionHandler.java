package com.example.boomermathblog.web;

import com.example.boomermathblog.exception.custom.AuthException;
import com.example.boomermathblog.exception.custom.BlogCustomException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class BlogGraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof BlogCustomException) {
            GraphqlErrorBuilder<?> graphqlErrorBuilder = GraphqlErrorBuilder.newError();

            if (ex instanceof AuthException) {
                graphqlErrorBuilder.errorType(ErrorType.UNAUTHORIZED);
            } else {
                graphqlErrorBuilder.errorType(ErrorType.BAD_REQUEST);
            }

            graphqlErrorBuilder.message(ex.getMessage())
                    .path(env.getExecutionStepInfo().getPath())
                    .location(env.getField().getSourceLocation())
                    .build();

            return graphqlErrorBuilder.build();
        }

        return null;
    }
}
