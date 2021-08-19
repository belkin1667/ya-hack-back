package com.belkin.yahack.api.controller;

import java.util.List;

import com.belkin.yahack.api.dto.response.EpisodeMetadataResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MockController {

    @GetMapping("/mock/episodes")
    public EpisodeMetadataResponse getEpisodeMetadata(@RequestParam(value = "preview", defaultValue = "false") Boolean preview) {
        EpisodeMetadataResponse response;
        if (preview)
            response = new EpisodeMetadataResponse("RgNC40LLQtdG", 1, "Sebrant Chatting", 17);
        else
            response = new EpisodeMetadataResponse("RgNC40LLQtdG", 1, "Sebrant Chatting", 17,
                    204695,
                    "https://anchor.fm/s/2ea5680/podcast/play/285191/https%3A%2F%2Fd3ctxlq1ktw2nl.cloudfront.net%2Fproduction%2F2018-2-19%2F1913605-44100-1-66946be5b8527.mp3",
                    "Описание", List.of("0LvQvtGF0LrQ", "tdC60LvQvtGF"));

        return response;
    }
}
