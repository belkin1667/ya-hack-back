package com.belkin.yahack.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.ArrayList;
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
     * base64-ID
     * <p>
     * Generated on first object creation
     */
    @Id
    @GenericGenerator(name = "base64_id", strategy = "com.belkin.yahack.model.generator.Base64Generator")
    @GeneratedValue(generator = "base64_id")
    @Column(name = "podcast_id", columnDefinition = "VARCHAR")
    private String id;

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

    @OneToMany(targetEntity = Episode.class, fetch = FetchType.LAZY)
    private List<Episode> episodes = new ArrayList<>();

    public void addEpisodes(List<Episode> newEpisodes) {
        episodes.addAll(newEpisodes);
    }

}
