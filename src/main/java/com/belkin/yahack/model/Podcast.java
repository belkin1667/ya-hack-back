package com.belkin.yahack.model;

import java.util.Base64;
import java.util.List;

public class Podcast { //<channel> tag in RSS feed

    // Generated on first object creation
    Base64 id;

    // Updated when RSS feed updated
    String lastUpdatedTime;

    // Get from user
    String title;
    String author;
    String description;
    String rss;

    // Get from RSS
    String link;
    String imageUrl;
    String generator;
    String lastBuildDate;
    List<Episode> episodes; //OneToMany

}
