package com.belkin.yahack.api.controller;

import java.util.List;

import com.belkin.yahack.api.dto.response.EpisodeMetadataResponse;
import com.belkin.yahack.api.dto.response.InteractiveImageButtonResponse;
import com.belkin.yahack.api.dto.response.InteractivePollResponse;
import com.belkin.yahack.api.dto.response.PodcastMetadataResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/podcasts")
public class ListenController {

    @GetMapping
    public List<String> getPodcastsIds() {
        return List.of("Podcasts", "ids", "here");
    }

    @GetMapping("/{base64id}")
    public PodcastMetadataResponse getPodcastMetadata(@PathVariable("base64id") String podcastId,
                                                      @RequestParam(value = "preview", defaultValue = "false") Boolean preview) {
        PodcastMetadataResponse response;
        if (preview)
            response = new PodcastMetadataResponse(podcastId, "Тайтл", "хттпс://имагеурл.хир/айди");
        else
            response = new PodcastMetadataResponse(podcastId, "Тайтл",  "хттпс://имагеурл.хир/айди", "Описание", List.of(1, 2, 3));

        return response;
    }

    @GetMapping("/{base64id}/episodes")
    public List<String> getEpisodesIds(@PathVariable("base64id") String podcastId) {
        return List.of("Episodes", "ids", "here");
        //только эпизоды с published=true
    }

    @GetMapping("/{base64id}/{id}")
    public EpisodeMetadataResponse getEpisodeMetadata(@PathVariable("base64id") String podcastId,
                                     @PathVariable("id") Integer episodeId,
                                     @RequestParam(value = "preview", defaultValue = "false") Boolean preview) {
        EpisodeMetadataResponse response;
        if (preview)
            response = new EpisodeMetadataResponse(podcastId, episodeId, "Тайтл", 123);
        else
            response = new EpisodeMetadataResponse(podcastId, episodeId, "Тайтл", 123, 456, "хттпс://мп3урл.хир/айди", "Описание",
                    List.of(new InteractiveImageButtonResponse("GatQLL04CNgR", 0, 5, "imagebutton", "https://upload.wikimedia.org/wikipedia/commons/1/17/Vladimir_Putin_%282018-03-01%29_03_%28cropped%29.jpg", "Buy now!", "https://google.com/"),
                            new InteractivePollResponse("GatgLL04DNgR", 5, 10, "poll", "Где хочешь жить?", List.of("Moscow", "London", "Los Angeles"), false, List.of(1)),
                            new InteractiveImageButtonResponse("GdtCLL04CNgR", 10, 15, "image", "https://upload.wikimedia.org/wikipedia/commons/1/17/Vladimir_Putin_%282018-03-01%29_03_%28cropped%29.jpg", null, null),
                            new InteractiveImageButtonResponse("GdtALL04CNgR", 15, 17, "button", null, "Buy now!", "https://google.com/")), true);

        return response;
    }

}
