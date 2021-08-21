package com.belkin.yahack.exception.access_denied;

import org.springframework.security.core.AuthenticationException;

public class AccessDeniedException extends AuthenticationException {

    public AccessDeniedException(String msg) {
        super(msg);
    }

}
