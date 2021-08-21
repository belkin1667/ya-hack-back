package com.belkin.yahack.exception.already_exists;

import com.belkin.yahack.exception.RestException;
import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends RestException {

    public AlreadyExistsException(String resource) {
        super(HttpStatus.CONFLICT, "Resource '" + resource + "' already exists");
    }

    public AlreadyExistsException(String resource, String attribute, String attributeValue) {
        super(HttpStatus.CONFLICT, "Resource '" + resource + "' with '" + attribute + "=" + attributeValue + "' already exists");
    }
}
