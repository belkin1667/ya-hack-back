package com.belkin.yahack.api.dto.response;

import java.util.HashSet;
import java.util.Set;

import com.belkin.yahack.model.Episode;
import com.belkin.yahack.model.InteractiveImageButton;
import com.belkin.yahack.model.InteractiveItem;
import com.belkin.yahack.model.InteractivePoll;
import com.belkin.yahack.model.InteractiveText;
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
        items = new HashSet<>();
        for (InteractiveItem item : episode.getItems()) {
            if (item instanceof InteractivePoll)
                items.add(new InteractivePollResponse((InteractivePoll) item));
            if (item instanceof InteractiveImageButton)
                items.add(new InteractiveImageButtonResponse((InteractiveImageButton) item));
            if (item instanceof InteractiveText)
                items.add(new InteractiveTextResponse((InteractiveText) item));
        }
    }

    public EpisodeMetadataResponse(String guid, String title, Long duration) {
        this.guid = guid;
        this.title = title;
        this.duration = duration;
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

    private Set<InteractiveItemResponse> items;

    private String defaultImageUrl;


    public static EpisodeMetadataResponse map(Episode episode) {
        return new EpisodeMetadataResponse(episode.getGuid(),
                episode.getTitle(),
                episode.getDuration());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EpisodeMetadataResponse) {
            return ((EpisodeMetadataResponse) obj).guid.equals(guid);
        }
        return false;
    }
}