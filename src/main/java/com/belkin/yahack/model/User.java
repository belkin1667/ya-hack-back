package com.belkin.yahack.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "myuser")
public class User {

    private final String DEFAULT_PROFILE_PHOTO_URL = "";
    private final ProfileAccess DEFAULT_PROFILE_ACCESS = ProfileAccess.ALL;

    @NonNull
    @Column(columnDefinition = "VARCHAR")
    @Id private String username;

    @Column(columnDefinition = "VARCHAR")
    private String phone;

    @Column(columnDefinition = "VARCHAR")
    private String title;

    @Column(columnDefinition = "VARCHAR")
    private String description;

    @NonNull
    @Column(name = "profile_id", columnDefinition = "VARCHAR")
    private String profilePhotoUrl = DEFAULT_PROFILE_PHOTO_URL;

    @NonNull
    @Column(name = "profile_access")
    private ProfileAccess profileAccess = DEFAULT_PROFILE_ACCESS;

    public User(String username) {
        log.info("Creating user...");

        this.username = username;
    }

    public enum ProfileAccess {
        ALL,
        NONE,
        MUTUAL_FOLLOWERS
    }
}


