package com.belkin.yahack.api.dto.response;

import java.util.List;

import com.belkin.yahack.model.InteractivePoll;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InteractivePollResponse extends InteractiveItemResponse {

    public InteractivePollResponse(InteractivePoll poll) {
        super(poll.getId(), poll.getTimeStart(), poll.getTimeEnd(), "poll");
        this.question = poll.getQuestion();
        this.options = poll.getOptions();
        this.multipleOptions = poll.isMultipleOptions();
        this.correctAnswers = poll.getCorrectAnswers();
    }

    public InteractivePollResponse(String id, Integer timeStart, Integer timeEnd, String type,  String question, List<String> options, boolean multipleOptions, List<Integer> correctAnswers) {
        super(id, timeStart, timeEnd, type);
        this.question = question;
        this.options = options;
        this.multipleOptions = multipleOptions;
        this.correctAnswers = correctAnswers;
    }

    private String question;
    private List<String> options;
    private boolean multipleOptions;
    private List<Integer> correctAnswers;
}
