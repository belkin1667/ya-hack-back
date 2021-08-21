package com.belkin.yahack.exception.not_found;

public class UserNotFoundException extends ResourceNotFoundException {

    public UserNotFoundException() {
        super("User");
    }

    public UserNotFoundException(String missingUsername) {
        super("User", "username", missingUsername);
    }
}
