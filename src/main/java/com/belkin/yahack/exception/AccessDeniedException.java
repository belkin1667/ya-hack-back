package com.belkin.yahack.exception;

import org.springframework.security.core.AuthenticationException;

public class AccessDeniedException extends AuthenticationException {

    public AccessDeniedException(String username) {
        super("User " + username + " is not owner of the resource");
    }
}
