package com.belkin.yahack.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@Entity
@Table(name="interactive_poll")
@NoArgsConstructor
public class InteractivePoll extends InteractiveItem {

    String question;
    String options; //List<String>
    boolean multipleOptions;
    String correctAnswers; //List<Integer>

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