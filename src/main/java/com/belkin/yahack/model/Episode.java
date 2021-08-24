package com.belkin.yahack.model;

import com.belkin.yahack.api.dto.response.EpisodeMetadataResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Entity of Episode, contains collection of {@link InteractiveItem}
 * <p>
 * {@literal <}item{@literal >} tag in RSS feed
 */
@Getter
@Setter
@Entity
@Table(name = "episodes")
public class Episode {

    public Episode() {
        this.published = false;
    }

    @Id
    private String guid;
    /**
     * The position (0 .. inf) of <item> tag in RSS feed
     */
    private Integer episodeNumber;
    private Long duration;
    private Long length;
    private String pubDate;
    private String url;
    private String description;
    private String title;

    @ManyToOne
    @JoinColumn(name="podcast_id")
    private Podcast podcast;


    @OneToMany(mappedBy = "episode", fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    private List<InteractiveItem> items;

    private boolean published; // can edit items while not published, as soon as published can't edit items
}
