package com.belkin.yahack.api.dto.response;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.belkin.yahack.model.Episode;
import com.belkin.yahack.model.Podcast;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class PodcastMetadataResponseWithEpisodes {

    public PodcastMetadataResponseWithEpisodes(Podcast podcast) {
        this.id = podcast.getId();
        this.title = podcast.getTitle();
        this.imageUrl = podcast.getImageUrl();
        this.description = podcast.getDescription();
        Set<Episode> eps = podcast.getEpisodes();
        this.episodesPreviews = eps.stream().filter(Episode::isPublished).map(EpisodeMetadataResponse::map).collect(Collectors.toList());
    }

    public PodcastMetadataResponseWithEpisodes(String id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    private String id;
    private String title;
    private String imageUrl;

    private String description;
    private List<EpisodeMetadataResponse> episodesPreviews;
}
