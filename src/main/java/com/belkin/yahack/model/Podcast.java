package com.belkin.yahack.model;

import com.belkin.yahack.api.dto.request.PodcastCreationRequest;
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

    public Podcast(PodcastCreationRequest podcast, String author) {
        this.rss = podcast.getRss();
        this.title = podcast.getTitle();
        this.description = podcast.getDescription();
        this.author = author;
        this.episodes = new ArrayList<>();
    }

    /**
     * base64-ID
     * <p>
     * Generated on first object creation
     */
    @Id
    @GenericGenerator(name = "base64_id", strategy = "com.belkin.yahack.model.generator.Base64Generator")
    @GeneratedValue(generator = "base64_id")
    @Column(name = "id", columnDefinition = "VARCHAR")
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

    @OneToMany(mappedBy = "podcast", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Episode> episodes;

    public void addEpisodes(List<Episode> newEpisodes) {
        episodes.addAll(newEpisodes);
    }

}
