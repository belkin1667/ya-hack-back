package com.belkin.yahack.model;

import com.belkin.yahack.api.dto.request.PodcastCreationRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
        this.episodes = new HashSet<>();
    }

    /**
     * base64-ID
     * <p>
     * Generated on first object creation
     */
    @Id
    @GenericGenerator(name = "podcast_base64_id", strategy = "com.belkin.yahack.model.generator.Base64Generator")
    @GeneratedValue(generator = "podcast_base64_id")
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

    @OneToMany(mappedBy = "podcast", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Episode> episodes;


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Podcast) {
            return ((Podcast) obj).id.equals(id);
        }
        return false;
    }
}
