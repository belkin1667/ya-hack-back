package com.belkin.yahack.security.exception;

import com.belkin.yahack.exception.RestException;
import org.springframework.http.HttpStatus;

public class JwtTokenWasNotProvidedException extends RestException {
    public JwtTokenWasNotProvidedException() {
        super(HttpStatus.FORBIDDEN, "Token was not provided in 'Authorization' Header");
    }
}
