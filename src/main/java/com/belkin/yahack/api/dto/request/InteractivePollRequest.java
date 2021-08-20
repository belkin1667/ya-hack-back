package com.belkin.yahack.api.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InteractivePollRequest extends InteractiveItemRequest {
    private String question;
    private List<String> options;
    private boolean multipleOptions;
    private List<Integer> correctAnswers;
}
