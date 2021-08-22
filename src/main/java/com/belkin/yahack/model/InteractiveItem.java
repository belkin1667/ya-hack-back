package com.belkin.yahack.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Entity
@NoArgsConstructor
@Table(name = "interactive_item")
public class InteractiveItem {

    @Id
    String id;
    Integer timeStart;
    Integer timeEnd;

    @ManyToOne
    @JoinColumn(name="episode_guid", nullable = false)
    private Episode episode;

}
