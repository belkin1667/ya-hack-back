package com.belkin.yahack.api.controller;

import java.util.List;

import com.belkin.yahack.model.Podcast;
import com.belkin.yahack.serivce.PodcastManagementService;
import com.belkin.yahack.serivce.rss.RssUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RssUpdater rssUpdater;
    private final PodcastManagementService podcastManagementService;

    @PostMapping(value = "/test/rss", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Podcast testRssReader() {
        Podcast podcast = new Podcast();
        podcast.setId("ididi");
        podcast.setRss("https://anchor.fm/s/2ea5680/podcast/rss");
        rssUpdater.update(podcast);
        return podcast;
    }

    @GetMapping(path="/test")
    public String test(@RequestHeader("username") String username) {
        return "TEST! Hello " + username;
    }


    @GetMapping(path="/edit/test")
    public String editTest() { return "EDIT TEST!"; }



    @GetMapping(path="/test/get_all_podcasts")
    public List<Podcast> getAllPodcasts() {
        return podcastManagementService.getAllPodcasts();
    }
}
