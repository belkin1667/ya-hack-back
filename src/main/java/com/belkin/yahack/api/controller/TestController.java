package com.belkin.yahack.api.controller;

import com.belkin.yahack.model.Podcast;
import com.belkin.yahack.serivce.RssUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final RssUpdater rssUpdater;

    @PostMapping("/test/rss")
    public Podcast testRssReader() {
        Podcast podcast = new Podcast();
        podcast.setId("id");
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


}
