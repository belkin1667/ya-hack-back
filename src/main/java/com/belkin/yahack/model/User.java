package com.belkin.yahack.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "myuser")
public class User {

    @Value("${model.user.defaultProfilePhotoUrl}")
    private String DEFAULT_PROFILE_PHOTO_URL;

    @Column(columnDefinition = "VARCHAR")
    @Id private String username;

    @Column(name = "profile_photo_url", columnDefinition = "VARCHAR")
    private String profilePhotoUrl = DEFAULT_PROFILE_PHOTO_URL;

    public User(String username) {
        this.username = username;
    }
}


