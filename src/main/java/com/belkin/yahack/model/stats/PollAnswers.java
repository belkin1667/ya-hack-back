package com.belkin.yahack.model.stats;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter @Setter
public class PollAnswers extends StatisticRecord {

    public PollAnswers(String pollId, String username, List<Integer> answers) {
        super(pollId, username);
        this.answers = answers;
    }

    @ElementCollection
    private List<Integer> answers;

}
