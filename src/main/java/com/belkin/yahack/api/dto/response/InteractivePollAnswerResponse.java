package com.belkin.yahack.api.dto.response;

import java.util.List;

import com.belkin.yahack.model.InteractivePoll;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InteractivePollAnswerResponse extends InteractivePollResponse {

    public InteractivePollAnswerResponse(InteractivePoll poll, List<Integer> stats) {
        super(poll, false);
        this.correct = poll.getCorrectAnswers();
        this.stats = stats;
    }

    private List<Integer> stats; //sum up to 100
    private List<Integer> correct;

}
