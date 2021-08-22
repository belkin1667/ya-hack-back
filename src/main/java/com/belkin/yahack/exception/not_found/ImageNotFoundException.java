package com.belkin.yahack.exception.not_found;

public class ImageNotFoundException extends ResourceNotFoundException {

    public ImageNotFoundException() {
        super("Image");
    }

    public ImageNotFoundException(String id) {
        super("Image", "Id", id);
    }

}
