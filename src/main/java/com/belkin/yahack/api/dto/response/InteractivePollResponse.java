package com.belkin.yahack.api.dto.response;

import java.util.List;

import com.belkin.yahack.model.InteractivePoll;
import lombok.Setter;
import lombok.Getter;

@Getter @Setter
public class InteractivePollResponse extends InteractiveItemResponse {

    public InteractivePollResponse(InteractivePoll poll) {
        super(poll.getId(), poll.getTimeStart(), poll.getTimeEnd(), "poll");
        this.question = poll.getQuestion();
        this.options = poll.getOptions();
        this.multipleOptions = poll.isMultipleOptions();
    }

    public InteractivePollResponse(String id, Integer timeStart, Integer timeEnd, String type,  String question, List<String> options, boolean multipleOptions, List<Integer> correctAnswers) {
        super(id, timeStart, timeEnd, type);
        this.question = question;
        this.options = options;
        this.multipleOptions = multipleOptions;
    }

    private String question;
    private List<String> options;
    private boolean multipleOptions;
}
