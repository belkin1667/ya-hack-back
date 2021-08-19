package com.belkin.yahack.model;

import java.util.List;

public class InteractivePoll extends InteractiveItem {

    String question;
    List<String> options;
    boolean multipleOptions;
    List<Integer> correctAnswers;

}
