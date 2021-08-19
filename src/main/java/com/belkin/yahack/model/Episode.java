package com.belkin.yahack.model;

import java.util.List;

public class Episode { // <item> tag in RSS feed

    // Get from RSS
    Integer id; // The position (0 .. inf) of <item> tag in RSS feed
    Integer duration;
    Integer length;
    String pubDate;
    String url;
    String description;
    String title;

    List<InteractiveItem> items; //OneToMany

    boolean published; // can edit items while not published, as soon as published can't edit items
}
