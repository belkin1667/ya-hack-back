package com.belkin.yahack.exception.not_found;

public class PodcastNotFoundException extends ResourceNotFoundException {

    public PodcastNotFoundException() {
        super("Podcast");
    }

    public PodcastNotFoundException(String missingId) {
        super("Podcast", "id", missingId);
    }

}
