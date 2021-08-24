package com.belkin.yahack.model.stats;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="stats")
@NoArgsConstructor
@Getter @Setter
public class StatisticRecord {

    public StatisticRecord(String elementId, String username) {
        this.elementId = elementId;
        this.username = username;
        timestamp = new Date();
    }

    @Id
    @GeneratedValue
    private Long id;

    private String elementId;
    private String username;

    private Date timestamp;
}
