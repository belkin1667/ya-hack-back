package com.belkin.yahack.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.belkin.yahack.api.dto.request.InteractivePollRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@Entity
@NoArgsConstructor
public class InteractivePoll extends InteractiveItem {


    public InteractivePoll(InteractivePollRequest interactivePollRequest) {
        super(interactivePollRequest);
        this.question = interactivePollRequest.getQuestion();
        this.options = interactivePollRequest.getOptions();
        this.correctAnswers = interactivePollRequest.getCorrectAnswers();
        this.multipleOptions = interactivePollRequest.isMultipleOptions();
    }
    @Column(name = "question", columnDefinition = "VARCHAR")
    private String question;
    private boolean multipleOptions;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> options;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Integer> correctAnswers;

}