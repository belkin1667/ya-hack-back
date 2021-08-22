package com.belkin.yahack.api.controller;

import java.util.List;

import com.belkin.yahack.api.dto.request.InteractiveImageButtonRequest;
import com.belkin.yahack.api.dto.request.InteractivePollRequest;
import com.belkin.yahack.api.dto.request.PodcastCreationRequest;
import com.belkin.yahack.api.dto.response.EpisodeMetadataResponse;
import com.belkin.yahack.api.dto.response.InteractiveImageButtonResponse;
import com.belkin.yahack.api.dto.response.InteractivePollResponse;
import com.belkin.yahack.api.dto.response.PodcastMetadataResponse;
import com.belkin.yahack.serivce.PodcastManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/edit/podcasts")
@RequiredArgsConstructor
public class EditController {

    private final PodcastManagementService podcastManagementService;

    @PostMapping
    public void addNewRss(@RequestBody PodcastCreationRequest podcastCreationRequest, @RequestHeader("username") String author) {
        podcastManagementService.addPodcast(podcastCreationRequest, author);
    }

    @GetMapping
    public List<String> getMyPodcastsIds(@RequestHeader("username") String author) {
        return podcastManagementService.getMyPodcastsIds(author);
    }

    @GetMapping("/{base64id}")
    public PodcastMetadataResponse getPodcastMetadata(@PathVariable("base64id") String podcastId,
                                                      @RequestParam(value = "preview", defaultValue = "false") Boolean preview,
                                                      @RequestHeader("username") String username) {
        if (preview)
            return podcastManagementService.getPodcastPreview(podcastId, username);
        else
            return podcastManagementService.getPodcast(podcastId, username);
    }


    @GetMapping("/{base64id}/episodes")
    public List<String> getEpisodesIds(@PathVariable("base64id") String podcastId) {
        return List.of("Episodes", "ids", "here");
    }

    @GetMapping("/{base64id}/{id}")
    public EpisodeMetadataResponse getEpisodeMetadata(@PathVariable("base64id") String podcastId,
                                                      @PathVariable("id") Integer episodeNumber,
                                                      @RequestParam(value = "preview", defaultValue = "false") Boolean preview) {
        EpisodeMetadataResponse response;
        if (preview)
            response = null;
        else
            response = null;

        return response;
    }


    @PostMapping("/{base64id}/{id}/imagebutton")
    public String addInteractiveImageButton(@PathVariable("base64id") String podcastId,
                                            @PathVariable("id") Integer episodeNumber,
                                            @RequestBody InteractiveImageButtonRequest imageButtonRequest) {
        return "generated id here";
    }

    @PostMapping("/{base64id}/{id}/poll")
    public String addInteractivePoll(@PathVariable("base64id") String podcastId,
                                     @PathVariable("id") Integer episodeNumber,
                                     @RequestBody InteractivePollRequest pollRequest) {
        return "generated id here";
    }


    @PutMapping("/{base64id}/{id}/imagebutton/{base64idItem}")
    public void editInteractiveImageButton(@PathVariable("base64id") String podcastId,
                                            @PathVariable("id") Integer episodeNumber,
                                            @PathVariable("base64idItem") String itemId,
                                            @RequestBody InteractiveImageButtonRequest imageButtonRequest) {
        /* В Request Body должны быть указаны только изменяемые поля */
    }


    @PutMapping("/{base64id}/{id}/poll/{base64idItem}")
    public void editInteractivePoll(@PathVariable("base64id") String podcastId,
                                          @PathVariable("id") Integer episodeNumber,
                                          @PathVariable("base64idItem") String itemId,
                                          @RequestBody InteractivePollRequest imageButtonRequest) {
        /* В Request Body должны быть указаны только изменяемые поля */
    }


    @PatchMapping("/{base64id}/{id}/imagebutton/{base64idItem}")
    public void updateInteractiveImageButton(@PathVariable("base64id") String podcastId,
                                           @PathVariable("id") Integer episodeNumber,
                                           @PathVariable("base64idItem") String itemId,
                                           @RequestBody InteractiveImageButtonRequest imageButtonRequest) {
        /* В Request Body должны быть указаны все поля элемента (кроме, очевидно, id элемента) */
    }


    @PatchMapping("/{base64id}/{id}/poll/{base64idItem}")
    public void updateInteractivePoll(@PathVariable("base64id") String podcastId,
                                    @PathVariable("id") Integer episodeNumber,
                                    @PathVariable("base64idItem") String itemId,
                                    @RequestBody InteractivePollRequest imageButtonRequest) {
        /* В Request Body должны быть указаны все поля элемента (кроме, очевидно, id элемента) */
    }


    @DeleteMapping("/{base64id}/{id}/imagebutton/{base64idItem}")
    public void deleteInteractiveImageButton(@PathVariable("base64id") String podcastId,
                                             @PathVariable("id") Integer episodeNumber,
                                             @PathVariable("base64idItem") String itemId) {
    }


    @DeleteMapping("/{base64id}/{id}/poll/{base64idItem}")
    public void deleteInteractivePoll(@PathVariable("base64id") String podcastId,
                                      @PathVariable("id") Integer episodeNumber,
                                      @PathVariable("base64idItem") String itemId) {

    }

    @PostMapping("/{base64id}/{id}/publish")
    public void publishEpisode(@PathVariable("base64id") String podcastId,
                               @PathVariable("id") Integer episodeNumber) {
        /*
        По дефолту все эпизоды не публикуются в паблик сразу, а доступны только авторам.
        После публикации же, изменять интерактивные элементы будет нельзя
        (простейший механизм защиты от неконсистентности данных)
         */
    }
}
