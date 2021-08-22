package com.belkin.yahack.api.dto.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.belkin.yahack.model.Episode;
import com.belkin.yahack.model.InteractiveImageButton;
import com.belkin.yahack.model.InteractiveItem;
import com.belkin.yahack.model.InteractivePoll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class EpisodeMetadataResponse {

    public EpisodeMetadataResponse(Episode episode) {
        this(episode.getGuid(),
                episode.getPodcast().getId(),
                episode.getEpisodeNumber(),
                episode.getTitle(),
                episode.getDuration(),
                episode.getLength(),
                episode.getUrl(),
                episode.getDescription(),
                episode.isPublished(),
                null,
                episode.getPodcast().getImageUrl());
        items = new ArrayList<>();
        for (InteractiveItem item : episode.getItems()) {
            if (item instanceof InteractivePoll)
                items.add(new InteractivePollResponse((InteractivePoll) item));
            if (item instanceof InteractiveImageButton)
                items.add(new InteractiveImageButtonResponse((InteractiveImageButton) item));
        }
    }

    public EpisodeMetadataResponse(String guid, String podcastId, Integer episodeNumber, String title, Long duration) {
        this.guid = guid;
        this.podcastId = podcastId;
        this.episodeNumber = episodeNumber;
        this.title = title;
        this.duration = duration;
    }

    private String guid;
    private String podcastId;
    private Integer episodeNumber;
    private String title;
    private Long duration;

    private Long length;
    private String url;
    private String description;
    private boolean published;

    private List<InteractiveItemResponse> items;

    private String defaultImageUrl;

}

/*
{
    "podcastId": "base64",
    "episodeId": "base64",
    "episodeNumber": 1,
    "title": "Cool podcast",
    "duration": 17,
    "url": ".mp3",
    "description": "blah-blah-blah",
    "published": true,
    "items": [
        { //InteractivePollResponse
            "id": "base64",
            "timeStart": 0,
            "timeEnd": 4,
            "type": "poll",
            "question": "Hmm??",
            "options": ["A", "B", "C"],
            "multipleOptions": true
        },
        { //InteractiveImagebuttonResponse
            "id": "base64",
            "timeStart": 4,
            "timeEnd": 8,
            "type": "imagebutton",
            "imageUrl": "putin.png",
            "buttonUrl": null,
            "buttonText": null
        },
        { //InteractiveImagebuttonResponse
            "id": "base64",
            "timeStart": 8,
            "timeEnd": 12,
            "type": "imagebutton",
            "imageUrl": null,
            "buttonUrl": "www.amazon.com/shop",
            "buttonText": "Купи скайрим"
        },
        { //InteractiveImagebuttonResponse
            "id": "base64",
            "timeStart": 12,
            "timeEnd": 17,
            "type": "imagebutton",
            "imageUrl": "putin.png",
            "buttonUrl": "www.amazon.com/shop",
            "buttonText": "Купи скайрим"
        },
        { //InteractiveTextResponse
            "id": "base64",
            "timeStart": 12,
            "timeEnd": 17,
            "type": "text",
            "text": "Купи скайрим"
        },
        { //InteractiveFormResponse
            "id": "base64",
            "timeStart": 12,
            "timeEnd": 17,
            "type": "form",
            "text": "Напиши что-нибудь"
        }
    ]
}
*/