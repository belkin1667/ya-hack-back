package com.belkin.yahack.api.controller;

import java.util.List;

import com.belkin.yahack.api.dto.response.EpisodeMetadataResponse;
import com.belkin.yahack.api.dto.response.InteractiveImageButtonResponse;
import com.belkin.yahack.api.dto.response.InteractivePollResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock")
public class MockController {

    @GetMapping("/episodes")
    public EpisodeMetadataResponse getEpisodeMetadata(@RequestParam(value = "preview", defaultValue = "false") Boolean preview) {
        EpisodeMetadataResponse response;
        if (preview)
            response = new EpisodeMetadataResponse("RgNC40LLQtdG", 1, "Sebrant Chatting", 17);
        else
            response = new EpisodeMetadataResponse("RgNC40LLQtdG", 1, "Sebrant Chatting", 17,
                    204695,
                    "https://anchor.fm/s/2ea5680/podcast/play/285191/https%3A%2F%2Fd3ctxlq1ktw2nl.cloudfront.net%2Fproduction%2F2018-2-19%2F1913605-44100-1-66946be5b8527.mp3",
                    "Описание",
                    List.of(new InteractiveImageButtonResponse("GatQLL04CNgR", 0, 5, "imagebutton", "https://upload.wikimedia.org/wikipedia/commons/1/17/Vladimir_Putin_%282018-03-01%29_03_%28cropped%29.jpg", "Buy now!", "https://google.com/"),
                            new InteractivePollResponse("GatgLL04DNgR", 5, 10, "poll", "Где хочешь жить?", List.of("Moscow", "London", "Los Angeles"), false, List.of(1)),
                            new InteractiveImageButtonResponse("GdtCLL04CNgR", 10, 15, "image", "https://upload.wikimedia.org/wikipedia/commons/1/17/Vladimir_Putin_%282018-03-01%29_03_%28cropped%29.jpg", null, null),
                            new InteractiveImageButtonResponse("GdtALL04CNgR", 15, 17, "button", null, "Buy now!", "https://google.com/")), true);

        return response;
    }
}
