package com.example.boomermathblog.exception;

import com.example.boomermathblog.exception.codes.AuthExceptions;
import com.example.boomermathblog.exception.custom.AuthException;
import org.springframework.security.core.AuthenticationException;

public class ExceptionBuilder {
    public static AuthenticationException authException(AuthExceptions authExceptions) {
        return new AuthException(authExceptions.name());
    }
}
