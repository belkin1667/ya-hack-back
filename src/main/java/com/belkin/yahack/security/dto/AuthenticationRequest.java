package com.belkin.yahack.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthenticationRequest {

    private String username;
    private String password;

}
