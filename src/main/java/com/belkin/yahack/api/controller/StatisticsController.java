package com.belkin.yahack.api.controller;

import java.util.List;

import com.belkin.yahack.api.dto.request.FormAnswerRequest;
import com.belkin.yahack.api.dto.request.PollAnswerRequest;
import com.belkin.yahack.api.dto.response.InteractivePollAnswerResponse;
import com.belkin.yahack.serivce.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PostMapping("/episodes/{guid}")
    public void incrementNumberOfEpisodeStarts(@PathVariable("guid") String episodeId,
                                               @RequestHeader("username") String username) {
        statisticsService.incrementEpisodePlays(episodeId, username);
    }

    @PostMapping("/buttons/{base64id}")
    public void incrementNumberOfButtonClicks(@PathVariable("base64id") String buttonId,
                                              @RequestHeader("username") String username) {
        statisticsService.incrementButtonClicks(buttonId, username);
    }

    @PostMapping("/polls/{base64id}")
    public InteractivePollAnswerResponse processPollAnswer(@PathVariable("base64id") String pollId,
                                                           @RequestBody PollAnswerRequest pollAnswer,
                                                           @RequestHeader("username") String username) {

        statisticsService.registerPollAnswer(pollId, pollAnswer.getAnswers(), username);
        return statisticsService.getPollResults(pollId);
    }
    
    @PostMapping("/forms/{base64id}")
    public void registerFormAnswer(@PathVariable("base64id") String formId,
                                   @RequestBody FormAnswerRequest formAnswer,
                                   @RequestHeader("username") String username) {
        statisticsService.registerFormAnswer(formId, formAnswer.getAnswer(), username);
    }

}
