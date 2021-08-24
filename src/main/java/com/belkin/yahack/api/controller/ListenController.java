package com.belkin.yahack.api.controller;

import java.util.List;

import com.belkin.yahack.api.dto.response.EpisodeMetadataResponse;
import com.belkin.yahack.api.dto.response.InteractiveImageButtonResponse;
import com.belkin.yahack.api.dto.response.InteractivePollResponse;
import com.belkin.yahack.api.dto.response.PodcastMetadataResponse;
import com.belkin.yahack.api.dto.response.PodcastMetadataResponseWithEpisodes;
import com.belkin.yahack.serivce.ListenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ListenController {

    private final ListenService listenService;

    @GetMapping("/podcasts")
    public List<String> getPodcastsIds() {
        return listenService.getPodcastIdList();
    }

    /*@GetMapping("/podcasts/{base64id}")
    public PodcastMetadataResponse getPodcastMetadata(@PathVariable("base64id") String podcastId,
                                                      @RequestParam(value = "preview", defaultValue = "false") Boolean preview) {
        if (preview)
            return listenService.getPodcastPreview(podcastId);
        else
            return listenService.getPodcast(podcastId);
    }*/


    @GetMapping("/podcasts/{base64id}")
    public PodcastMetadataResponseWithEpisodes getPodcastMetadata(@PathVariable("base64id") String podcastId,
                                                                  @RequestParam(value = "preview", defaultValue = "false") Boolean preview) {
        if (preview)
            return listenService.getPodcastPreview(podcastId);
        else
            return listenService.getPodcast(podcastId);
    }

    @GetMapping("/podcasts/{base64id}/episodes")
    public List<String> getEpisodesIds(@PathVariable("base64id") String podcastId) {
        return listenService.getEpisodesIds(podcastId); //только эпизоды с published=true
    }

    @GetMapping("/podcasts/{base64id}/{id}")
    public EpisodeMetadataResponse getEpisodeMetadata(@PathVariable("base64id") String podcastId,
                                     @PathVariable("id") Integer episodeNumber,
                                     @RequestParam(value = "preview", defaultValue = "false") Boolean preview) {
        if (preview)
            return listenService.getEpisodePreview(podcastId, episodeNumber);
        else
            return listenService.getEpisode(podcastId, episodeNumber);

    }

    @GetMapping("/episodes/{guid}")
    public EpisodeMetadataResponse getEpisodeMetadata(@PathVariable("guid") String episodeId,
                                                      @RequestParam(value = "preview", defaultValue = "false") Boolean preview,
                                                      @RequestHeader("username") String username) {
        if (preview)
            return listenService.getEpisodePreview(episodeId);
        else
            return listenService.getEpisode(episodeId, username);
    }

}
