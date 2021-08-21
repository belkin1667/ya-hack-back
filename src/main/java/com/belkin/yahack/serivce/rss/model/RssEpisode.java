package com.belkin.yahack.serivce.rss.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RssEpisode {
    private Integer episodeNumber;
    private String guid;
    private String title;
    private String description;
    private String link;
    private String pubDate;
    private String audioUrl;
    private Long audioLength;
    private String audioType;
    private Long duration;
    private Integer season;
    private String imageUrl;
    private String explicit;
    private String summary;
}
