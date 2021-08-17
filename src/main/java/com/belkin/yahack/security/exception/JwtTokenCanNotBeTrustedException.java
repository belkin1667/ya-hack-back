package com.belkin.yahack.security.exception;

import com.belkin.yahack.exception.MyRestException;
import org.springframework.http.HttpStatus;

public class JwtTokenCanNotBeTrustedException extends MyRestException {
    public JwtTokenCanNotBeTrustedException(String token) {
        super(HttpStatus.FORBIDDEN, "Token '" + token + "' can not be trusted");
    }
}
