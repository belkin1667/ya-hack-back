package com.belkin.yahack.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class PodcastCreationRequest {

    private String rss;
    private String title;
    private String description;

}
