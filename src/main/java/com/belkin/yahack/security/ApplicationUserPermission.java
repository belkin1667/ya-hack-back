package com.belkin.yahack.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ApplicationUserPermission {

    EDIT("edit:author"),
    READ("read:user");

    @Getter
    private final String permission;

}
