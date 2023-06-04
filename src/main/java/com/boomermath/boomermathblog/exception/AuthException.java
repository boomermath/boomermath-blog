package com.boomermath.boomermathblog.exception;

import com.boomermath.boomermathblog.exception.message.AuthExceptions;
import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.springframework.security.core.AuthenticationException;

import java.util.List;

public class AuthException extends AuthenticationException implements GraphQLError, BlogCustomException {
    public AuthException(AuthExceptions exception) {
        super(exception.name());
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return null;
    }
}
