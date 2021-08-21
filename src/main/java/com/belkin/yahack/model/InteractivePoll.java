package com.belkin.yahack.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class InteractivePoll extends InteractiveItem {

    String question;
    List<String> options;
    boolean multipleOptions;
    List<Integer> correctAnswers;

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