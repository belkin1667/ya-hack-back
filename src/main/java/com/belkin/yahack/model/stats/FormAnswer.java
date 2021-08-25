package com.belkin.yahack.model.stats;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class FormAnswer extends StatisticRecord {
    public FormAnswer(String formId, String username, String answer) {
        super(formId, username);
        this.answer = answer;
    }

    private String answer;
}
