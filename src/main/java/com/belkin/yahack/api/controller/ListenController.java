package com.belkin.yahack.api.controller;

import java.util.List;

import com.belkin.yahack.api.dto.response.EpisodeMetadataResponse;
import com.belkin.yahack.api.dto.response.PodcastMetadataResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListenController {

    @GetMapping("/podcasts")
    public List<String> getPodcastsIds() {
        return List.of("Podcasts", "ids", "here");
    }

    @GetMapping("/podcasts/{base64id}")
    public PodcastMetadataResponse getPodcastMetadata(@PathVariable("base64id") String podcastId,
                                                      @RequestParam(value = "preview", defaultValue = "false") Boolean preview) {
        PodcastMetadataResponse response;
        if (preview)
            response = new PodcastMetadataResponse(podcastId, "Тайтл", "хттпс://имагеурл.хир/айди");
        else
            response = new PodcastMetadataResponse(podcastId, "Тайтл",  "хттпс://имагеурл.хир/айди", "Описание", List.of(1, 2, 3));

        return response;
    }

    @GetMapping("/podcasts/{base64id}/episodes")
    public List<String> getEpisodesIds(@PathVariable("base64id") String podcastId) {
        return List.of("Episodes", "ids", "here");
    }

    @GetMapping("/podcasts/{base64id}/{id}")
    public EpisodeMetadataResponse getEpisodeMetadata(@PathVariable("base64id") String podcastId,
                                     @PathVariable("id") Integer episodeId,
                                     @RequestParam(value = "preview", defaultValue = "false") Boolean preview) {
        EpisodeMetadataResponse response;
        if (preview)
            response = new EpisodeMetadataResponse(podcastId, episodeId, "Тайтл", 123);
        else
            response = new EpisodeMetadataResponse(podcastId, episodeId, "Тайтл", 123, 456, "хттпс://мп3урл.хир/айди", "Описание", List.of("id1", "id2", "id3"));

        return response;
    }

}
