package com.belkin.yahack.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Podcast { //<channel> tag in RSS feed

    // Generated on first object creation
    String id;

    // Updated when RSS feed updated
    String lastUpdatedTime;

    // Get from user
    String title;
    String author;
    String description;
    String rss;

    // Get from RSS
    String link; //link
    String imageUrl; //image.url
    List<Episode> episodes; //OneToMany

}
