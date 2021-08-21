package com.belkin.yahack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entity of Episode, contains collection of {@link InteractiveItem}
 * <p>
 * {@literal <}item{@literal >} tag in RSS feed
 */
@Getter
@Setter
@NoArgsConstructor
public class Episode {

    // Get from RSS

    /**
     * The position (0 .. inf) of <item> tag in RSS feed
     */
    private Integer episodeNumber;
    private Integer duration;
    private Integer length;
    private String pubDate;
    private String url;
    private String description;
    private String title;

//    @OneToMany(targetEntity=InteractiveItem.class, fetch= FetchType.LAZY)
    private List<InteractiveItem> items;

    private boolean published; // can edit items while not published, as soon as published can't edit items
}
