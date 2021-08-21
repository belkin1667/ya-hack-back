package com.belkin.yahack.exception.not_found;

import com.belkin.yahack.exception.RestException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RestException {

    public ResourceNotFoundException(String resource) {
        super(HttpStatus.NOT_FOUND, "Resource '" + resource + "' not found");
    }

    public ResourceNotFoundException(String resource, String attribute, String missingAttributeValue) {
        super(HttpStatus.NOT_FOUND, "Resource '" + resource + "' with '" + attribute + "=" + missingAttributeValue + "' not found");
    }
}
