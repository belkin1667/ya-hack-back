package com.belkin.yahack.api;

import javax.servlet.http.HttpServletRequest;

import com.belkin.yahack.api.dto.response.ExceptionResponse;
import com.belkin.yahack.exception.RestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = RestException.class)
    protected ResponseEntity<Object> handleMyRestException(RestException exception, HttpServletRequest request) {
        log.warn("4xx error occurred. Message: " + exception.getMessage() + ". Status: " + exception.getStatus().value() + " " + exception.getStatus().getReasonPhrase());

        ExceptionResponse apiError = new ExceptionResponse(exception.getStatus(), exception.getMessage(), request.getServletPath());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException exception, HttpServletRequest request) {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        log.warn("4xx Authentication error occurred. Message: " + exception.getMessage() + ". Status: " + status.value() + " " + status.getReasonPhrase());

        ExceptionResponse apiError = new ExceptionResponse(status, exception.getMessage(), request.getServletPath());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handleException(Exception exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        log.error("5xx error occurred. Message: " + exception.getMessage() + ". Status: " + status.value() + " " + status.getReasonPhrase());

        ExceptionResponse apiError = new ExceptionResponse(status, exception.getMessage(), request.getServletPath());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}

