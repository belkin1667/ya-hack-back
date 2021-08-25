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

    public InteractivePollResponse(InteractivePoll poll, boolean canInteract) {
        super(poll.getId(), poll.getTimeStart(), poll.getTimeEnd(), "poll");
        this.question = poll.getQuestion();
        this.options = poll.getOptions();
        this.multipleOptions = poll.isMultipleOptions();
        this.canInteract = false;
    }

    private String question;
    private List<String> options;
    private boolean multipleOptions;

    private boolean canInteract = true;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
