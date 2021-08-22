package com.belkin.yahack.exception;

import org.springframework.http.HttpStatus;

public class ImageStorageException extends RuntimeException {

    public ImageStorageException(String filename) {
        super("Could not store image " + filename + ". Please try again");
    }
}