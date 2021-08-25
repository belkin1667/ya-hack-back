package com.belkin.yahack.api.dto.response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.belkin.yahack.model.Episode;
import com.belkin.yahack.model.Podcast;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@AllArgsConstructor
public class PodcastMetadataResponse {

    public PodcastMetadataResponse(Podcast podcast) {
        this.id = podcast.getId();
        this.title = podcast.getTitle();
        this.imageUrl = podcast.getImageUrl();
        this.description = podcast.getDescription();
        this.episodeGuids = podcast.getEpisodes().stream().map(Episode::getGuid).collect(Collectors.toSet());
    }

    public PodcastMetadataResponse(String id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    private String id;
    private String title;
    private String imageUrl;

    private String description;
    private Set<String> episodeGuids;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PodcastMetadataResponse) {
            return ((PodcastMetadataResponse) obj).id.equals(id);
        }
        return false;
    }
}
