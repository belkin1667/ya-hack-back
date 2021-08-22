package com.belkin.yahack.exception.not_found;

public class EpisodeNotFoundException extends ResourceNotFoundException {

    public EpisodeNotFoundException() {
        super("Episode");
    }

    public EpisodeNotFoundException(String missingId) {
        super("Episode", "id", missingId);
    }

}
