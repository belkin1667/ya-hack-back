package com.belkin.yahack.security;

import java.util.Set;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ApplicationUserRole {

    USER(Sets.newHashSet(ApplicationUserPermission.READ)),
    AUTHOR(Sets.newHashSet(ApplicationUserPermission.EDIT, ApplicationUserPermission.READ));

    @Getter
    private final Set<ApplicationUserPermission> permissions;

    /*public Set<? extends GrantedAuthority> getGrantedAuthorities() {
        Set<SimpleGrantedAuthority> grantedAuthorities = getPermissions().stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet());
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return grantedAuthorities;
    }*/
}
