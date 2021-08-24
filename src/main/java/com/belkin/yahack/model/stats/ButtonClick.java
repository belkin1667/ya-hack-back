package com.belkin.yahack.model.stats;


import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class ButtonClick extends StatisticRecord {

    public ButtonClick(String elementId, String username) {
        super(elementId, username);
    }
}
