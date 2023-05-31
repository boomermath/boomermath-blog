package com.boomermath.boomermathblog.exception;

import com.boomermath.boomermathblog.exception.except.AuthException;
import com.boomermath.boomermathblog.exception.message.AuthExceptions;
import org.springframework.security.core.AuthenticationException;

public class ExceptionBuilder {
    public static AuthenticationException authException(AuthExceptions authExceptions) {
        return new AuthException(authExceptions.name());
    }
}
