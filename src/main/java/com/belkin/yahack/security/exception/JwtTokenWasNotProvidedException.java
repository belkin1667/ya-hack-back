package com.belkin.yahack.security.exception;

import com.belkin.yahack.exception.MyRestException;
import org.springframework.http.HttpStatus;

public class JwtTokenWasNotProvidedException extends MyRestException {
    public JwtTokenWasNotProvidedException() {
        super(HttpStatus.FORBIDDEN, "Token was not provided in 'Authorization' Header");
    }
}
