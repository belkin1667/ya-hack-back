package com.belkin.yahack.model;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Slf4j
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ImageMetadata {


    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    public static final String BASE_PATH = "." + File.separator + "images";

    @Id
    @GenericGenerator(name = "image_base64_id", strategy = "com.belkin.yahack.model.generator.Base64Generator")
    @GeneratedValue(generator = "image_base64_id")
    String id;

    @Column(columnDefinition = "VARCHAR")
    String path;

    @Column(columnDefinition = "VARCHAR")
    String extension = ".jpg";

    @Column(name = "uploader", columnDefinition = "VARCHAR")
    String uploaderUsername;

    public ImageMetadata(String id, String uploaderUsername, String extension) {
        this.uploaderUsername = uploaderUsername;
        this.extension = extension;
        this.id = id;
    }

    public ImageMetadata(String uploaderUsername, String extension) {
            this.uploaderUsername = uploaderUsername;
            this.extension = extension;
    }

    public void setPath() {
        path = BASE_PATH + File.separator + id + extension;
    }
}

