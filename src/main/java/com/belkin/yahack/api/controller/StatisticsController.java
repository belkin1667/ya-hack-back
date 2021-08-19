package com.belkin.yahack.api.controller;

import java.util.List;

import com.belkin.yahack.api.dto.request.PollAnswerRequest;
import com.belkin.yahack.api.dto.response.PollAnswerResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    @PostMapping("/stats/episodes/{base64id}/{id}")
    public void incrementNumberOfEpisodeStarts(@PathVariable("base64id") String podcastId,
                                               @PathVariable("id") Integer episodeId) {

    }

    @PostMapping("/stats/buttons/{base64id}")
    public void incrementNumberOfButtonClicks(@PathVariable("base64id") String buttonId) {

    }

    @PostMapping("/stats/polls/{base64id}")
    public PollAnswerResponse processPollAnswer(@PathVariable("base64id") String pollId,
                                                @RequestBody PollAnswerRequest pollAnswer) {

        return new PollAnswerResponse(pollId, List.of(33, 12, 55));
    }

}
