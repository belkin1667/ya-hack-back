package com.belkin.yahack.security.exception;

import com.belkin.yahack.exception.RestException;
import org.springframework.http.HttpStatus;

public class UserAlreadyRegisteredException extends RestException {

    public UserAlreadyRegisteredException(String msg) {
        super(HttpStatus.CONFLICT, "'" + msg + "' is already registered username");
    }
}
