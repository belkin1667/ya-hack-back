package com.belkin.yahack.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Entity of Podcast, contains collection of {@link Episode}
 * <p>
 * {@literal <}channel{@literal >} tag in RSS feed
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "podcast")
public class Podcast {

    /**
     * Generated on first object creation
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // TODO: Change type to string (aka base64)
    private Integer id;

    /**
     * Updated when RSS feed updated
     */
    private String lastUpdatedTime;

    // Get from user
    private String title;
    private String author;
    private String description;
    private String rss;

    // Get from RSS
    private String link;
    private String imageUrl;
    private String generator;
    private String lastBuildDate;

//    @OneToMany(targetEntity = Episode.class,
//            fetch = FetchType.LAZY)
//    private List<Episode> episodes;

}
