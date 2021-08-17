package com.belkin.yahack.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.belkin.yahack.security.exception.JwtTokenCanNotBeTrustedException;
import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("Verifying JWS Token at endpoint" + request.getRequestURL().toString());
        String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
        if (Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
            log.info("JWS Token is not provided in Authentication Header");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
        try {
            JwsDecoder decoder = new JwsDecoder(token, secretKey);
            decoder.decode();
            Claims body = decoder.getBody();
            String username = body.getSubject();
            var authorities = (List<Map<String, String>>) body.get("authorities");
            Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = authorities.stream()
                    .map(a -> new SimpleGrantedAuthority(a.get("authority")))
                    .collect(Collectors.toSet());
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthoritySet);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("JWS Token is valid");
        } catch (RuntimeException e) {
            throw new JwtTokenCanNotBeTrustedException(token);
        }
        filterChain.doFilter(request, response); //todo как-то пробросить username в request чтобы не просить везде Authentication Header?
    }

    public String getRequesterUsername(String authorizationHeader) {
        log.info("Getting requester username...");

        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
        JwsDecoder decoder = new JwsDecoder(token, secretKey);
        decoder.decode();
        String username = decoder.getBody().getSubject();

        log.info("Requester username = '" + username + "' provided by JWT Token from Authentication header '" + authorizationHeader + "'");

        return username;
    }
}
