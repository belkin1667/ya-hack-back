package com.belkin.yahack.api.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.belkin.yahack.api.dto.request.InteractiveImageButtonRequest;
import com.belkin.yahack.api.dto.request.InteractiveItemRequest;
import com.belkin.yahack.api.dto.request.InteractivePollRequest;
import com.belkin.yahack.api.dto.request.PodcastCreationRequest;
import com.belkin.yahack.api.dto.response.EpisodeMetadataResponse;
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
@RequestMapping("/edit")
@RequiredArgsConstructor
public class EditController {

    private final PodcastManagementService podcastManagementService;

    @PostMapping("/podcasts")
    public void addNewRss(@RequestBody PodcastCreationRequest podcastCreationRequest, @RequestHeader("username") String author) {
        podcastManagementService.addPodcast(podcastCreationRequest, author);
    }

    @GetMapping("/podcasts")
    public List<String> getMyPodcastsIds(@RequestHeader("username") String author) {
        return podcastManagementService.getMyPodcastsIds(author);
    }

    @GetMapping("/podcasts/{base64id}")
    public PodcastMetadataResponse getPodcastMetadata(@PathVariable("base64id") String podcastId,
                                                      @RequestParam(value = "preview", defaultValue = "false") Boolean preview,
                                                      @RequestHeader("username") String username) {
        if (preview)
            return podcastManagementService.getPodcastPreview(podcastId, username);
        else
            return podcastManagementService.getPodcast(podcastId, username);
    }

    @GetMapping("/podcasts/{base64id}/episodes")
    public List<String> getEpisodesIds(@PathVariable("base64id") String podcastId,
                                       @RequestHeader("username") String username) {
        return podcastManagementService.getEpisodesIds(podcastId, username);
    }

    @GetMapping("/podcasts/{base64id}/{id}")
    public EpisodeMetadataResponse getEpisodeMetadata(@PathVariable("base64id") String podcastId,
                                                      @PathVariable("id") Integer episodeNumber,
                                                      @RequestParam(value = "preview", defaultValue = "false") Boolean preview,
                                                      @RequestHeader("username") String username) {
        if (preview)
            return podcastManagementService.getEpisodePreview(podcastId, episodeNumber, username);
        else
            return podcastManagementService.getEpisode(podcastId, episodeNumber, username);
    }


    @GetMapping("/episodes/{guid}")
    public EpisodeMetadataResponse getEpisodeMetadata(@PathVariable("guid") String episodeId,
                                                      @RequestParam(value = "preview", defaultValue = "false") Boolean preview,
                                                      @RequestHeader("username") String username) {
        if (preview)
            return podcastManagementService.getEpisodePreview(episodeId, username);
        else
            return podcastManagementService.getEpisode(episodeId, username);
    }


    @PostMapping("/episodes/{guid}/all")
    public Set<String> addInteractiveItems(@PathVariable("guid") String episodeId,
                                            @RequestBody Set<InteractiveItemRequest> items) {
        Set<String> ids = new HashSet<>();
        items.forEach(item -> ids.add(podcastManagementService.addInteractiveItem(episodeId, item)));
        return ids;
    }

    @PostMapping("/episodes/{guid}")
    public String addInteractiveItem(@PathVariable("guid") String episodeId,
                                            @RequestBody InteractiveItemRequest itemRequest) {
        return podcastManagementService.addInteractiveItem(episodeId, itemRequest);
    }


    @PutMapping("/{base64id}/{id}/imagebutton/{base64idItem}")
    public void editInteractiveImageButton(@PathVariable("base64id") String podcastId,
                                            @PathVariable("id") Integer episodeNumber,
                                            @PathVariable("base64idItem") String itemId,
                                            @RequestBody InteractiveImageButtonRequest imageButtonRequest) {
        /* ?? Request Body ???????????? ???????? ?????????????? ???????????? ???????????????????? ???????? */
    }


    @PutMapping("/{base64id}/{id}/poll/{base64idItem}")
    public void editInteractivePoll(@PathVariable("base64id") String podcastId,
                                          @PathVariable("id") Integer episodeNumber,
                                          @PathVariable("base64idItem") String itemId,
                                          @RequestBody InteractivePollRequest imageButtonRequest) {
        /* ?? Request Body ???????????? ???????? ?????????????? ???????????? ???????????????????? ???????? */
    }


    @PatchMapping("/{base64id}/{id}/imagebutton/{base64idItem}")
    public void updateInteractiveImageButton(@PathVariable("base64id") String podcastId,
                                           @PathVariable("id") Integer episodeNumber,
                                           @PathVariable("base64idItem") String itemId,
                                           @RequestBody InteractiveImageButtonRequest imageButtonRequest) {
        /* ?? Request Body ???????????? ???????? ?????????????? ?????? ???????? ???????????????? (??????????, ????????????????, id ????????????????) */
    }


    @PatchMapping("/{base64id}/{id}/poll/{base64idItem}")
    public void updateInteractivePoll(@PathVariable("base64id") String podcastId,
                                    @PathVariable("id") Integer episodeNumber,
                                    @PathVariable("base64idItem") String itemId,
                                    @RequestBody InteractivePollRequest imageButtonRequest) {
        /* ?? Request Body ???????????? ???????? ?????????????? ?????? ???????? ???????????????? (??????????, ????????????????, id ????????????????) */
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

    @PostMapping("/episodes/{guid}/publish")
    public void publishEpisode(@PathVariable("guid") String episodeId,
                               @RequestHeader("username") String username) {
        /*
        ???? ?????????????? ?????? ?????????????? ???? ?????????????????????? ?? ???????????? ??????????, ?? ???????????????? ???????????? ??????????????.
        ?????????? ???????????????????? ????, ???????????????? ?????????????????????????? ???????????????? ?????????? ????????????
        (???????????????????? ???????????????? ???????????? ???? ?????????????????????????????????? ????????????)
         */

        podcastManagementService.publishEpisode(episodeId, username);
    }
}
