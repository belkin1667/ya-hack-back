package com.belkin.yahack.exception.already_exists;

public class PodcastAlreadyExistsException extends AlreadyExistsException {

    public PodcastAlreadyExistsException(String attribute, String attributeValue ) {
        super("podcast", attribute, attributeValue);
    }
}
