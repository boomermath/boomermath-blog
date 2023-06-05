package com.boomermath.boomermathblog.web;

import com.boomermath.boomermathblog.exception.AuthException;
import com.boomermath.boomermathblog.exception.BlogCustomException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class BlogZGraphQLExceptionHandler {

    private GraphQLError createAuthenticationException(String message) {
        return GraphqlErrorBuilder.newError()
                .message(message)
                .errorType(ErrorType.UNAUTHORIZED)
                .build();
    }
    @ExceptionHandler
    public GraphQLError authException(AuthException authException) {
        return createAuthenticationException(authException.getMessage());
    }

    @ExceptionHandler
    public GraphQLError accessDeniedException(AccessDeniedException accessDeniedException) {
        return createAuthenticationException(accessDeniedException.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public GraphQLError customException(BlogCustomException blogCustomException) {
       return GraphqlErrorBuilder.newError()
               .message(blogCustomException.getMessage())
               .errorType(ErrorType.BAD_REQUEST)
               .build();
    }
}
