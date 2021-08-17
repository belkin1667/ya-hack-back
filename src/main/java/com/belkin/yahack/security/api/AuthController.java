package com.belkin.yahack.security.api;

import com.belkin.yahack.security.ApplicationUserRole;
import com.belkin.yahack.security.dto.AuthenticationRequest;
import com.belkin.yahack.security.dto.RegistrationRequest;
import com.belkin.yahack.security.exception.JwtTokenWasNotProvidedException;
import com.belkin.yahack.security.service.ApplicationUserService;
import io.swagger.annotations.ResponseHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final ApplicationUserService applicationUserService;

    @PostMapping("/register/user")
    public void registerUser(@RequestBody RegistrationRequest registrationRequest) {
        log.info("POST /auth/register/user");

        applicationUserService.register(registrationRequest, ApplicationUserRole.USER);
    }

    @PostMapping("/register/author")
    public void registerAuthor(@RequestBody RegistrationRequest registrationRequest) {
        log.info("POST /auth/register/author");

        applicationUserService.register(registrationRequest, ApplicationUserRole.AUTHOR);
    }

    // Endpoint /auth/login is managed by JwtAuthenticationFilter,
    // the following method is used to show correct API with Swagger
    @ResponseHeader(name = "Authentication", description = "Bearer <token>")
    @PostMapping("/login")
    public void login(@RequestBody AuthenticationRequest request) {

    }

    @GetMapping("/check/user")
    public void jwtCheckUser(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new JwtTokenWasNotProvidedException();
    }

    @GetMapping("/check/author")
    public void jwtCheckAuthor(@RequestHeader("Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
            throw new JwtTokenWasNotProvidedException();
    }

}
