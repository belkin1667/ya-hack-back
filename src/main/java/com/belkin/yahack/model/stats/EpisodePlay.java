package com.belkin.yahack.model.stats;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class EpisodePlay extends StatisticRecord {

    public EpisodePlay(String elementId, String username) {
        super(elementId, username);
    }
}
