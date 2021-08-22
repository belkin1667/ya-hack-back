package com.belkin.yahack.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.belkin.yahack.api.dto.request.InteractivePollRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@Entity
@Table(name="interactive_poll")
@NoArgsConstructor
public class InteractivePoll extends InteractiveItem {


    public InteractivePoll(InteractivePollRequest interactivePollRequest) {
        super(interactivePollRequest);
        this.question = interactivePollRequest.getQuestion();
        this.options = interactivePollRequest.getOptions();
        this.correctAnswers = interactivePollRequest.getCorrectAnswers();
        this.multipleOptions = interactivePollRequest.isMultipleOptions();
    }

    private String question;
    private boolean multipleOptions;

    @ElementCollection
    private List<String> options;

    @ElementCollection
    private List<Integer> correctAnswers;

}

/*


{
"id": "id",
"timeStart": 123,
"timeEnd": 123,
"question": "hm?",
"options": ["A", "B", "C"],
"multipleOptions":true,
}

{
"correctAnswers": [0, 2] // пусто или null - нет правильного ответа
"
}
 */