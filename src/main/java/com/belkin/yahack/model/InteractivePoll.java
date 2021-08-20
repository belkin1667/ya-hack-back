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
