package com.belkin.yahack.security.exception;

import com.belkin.yahack.exception.MyRestException;
import org.springframework.http.HttpStatus;

public class UserAlreadyRegisteredException extends MyRestException {

    public UserAlreadyRegisteredException(String msg) {
        super(HttpStatus.CONFLICT, "'" + msg + "' is already registered username");
    }
}
