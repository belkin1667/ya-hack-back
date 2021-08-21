package com.belkin.yahack.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Episode { // <item> tag in RSS feed

    public Episode(Integer id) {
        this.id = id;
        this.published = false;
    }
    // Get from RSS
    Integer id; // The position (0 .. inf) of <item> tag in RSS feed //pos in entries arr
    Long duration; //entries[0].foreignMarkup[2]
    Long length; //entries[0].enclosures[0].length
    String pubDate; //entries[0].modules[0].date
    String url; //entries[0].enclosures[0].url
    String description; //entries[0].description.value
    String title; //entries[0].title

    List<InteractiveItem> items; //OneToMany

    boolean published; // can edit items while not published, as soon as published can't edit items
}
