package com.belkin.yahack.api.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class InteractivePollAnswerResponse {

    //todo уточнить у фронтов что им нужно
    public InteractivePollAnswerResponse(String id, List<Integer> stats) {
        this.id = id;
        this.stats = stats;
    }

    private String id;
    private List<Integer> stats; //sum up to 100
    private Integer correct;

}
