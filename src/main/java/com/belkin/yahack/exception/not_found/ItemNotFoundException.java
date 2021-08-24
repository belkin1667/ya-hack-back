package com.belkin.yahack.exception.not_found;

public class ItemNotFoundException extends ResourceNotFoundException {

    public ItemNotFoundException() {
        super("Interactive item");
    }

    public ItemNotFoundException(String missingId) {
        super("Interactive item", "id", missingId);
    }
}
