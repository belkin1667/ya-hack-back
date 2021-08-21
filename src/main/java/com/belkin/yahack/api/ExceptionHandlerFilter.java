package com.belkin.yahack.api;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.belkin.yahack.api.dto.response.ExceptionResponse;
import com.belkin.yahack.exception.RestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (RestException e) {
            log.warn("4xx error occurred. Message: " + e.getMessage() + ". Status: " + e.getStatus().value() + " " + e.getStatus().getReasonPhrase());

            ExceptionResponse exceptionResponse = new ExceptionResponse(e.getStatus(), e.getMessage(), request.getServletPath());
            response.setStatus(e.getStatus().value());
            response.getWriter().write(jsonify(exceptionResponse));
            response.addHeader("Content-Type", "application/json");
        }
        catch (AuthenticationException e) {
            HttpStatus status = HttpStatus.UNAUTHORIZED;
            log.warn("4xx Authentication error occurred. Message: " + e.getMessage() + ". Status: " + status.value() + " " + status.getReasonPhrase());

            ExceptionResponse exceptionResponse = new ExceptionResponse(status, e.getMessage(), request.getServletPath());
            response.setStatus(status.value());
            response.getWriter().write(jsonify(exceptionResponse));
            response.addHeader("Content-Type", "application/json");
        }
        catch (RuntimeException e) {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            log.error("5xx error occurred. Message: " + e.getMessage() + ". Status: " + status.value() + " " + status.getReasonPhrase());

            ExceptionResponse exceptionResponse = new ExceptionResponse(status, e.getMessage(), request.getServletPath());
            response.setStatus(status.value());
            response.getWriter().write(jsonify(exceptionResponse));
            response.addHeader("Content-Type", "application/json");
        }
    }

    private String jsonify(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
