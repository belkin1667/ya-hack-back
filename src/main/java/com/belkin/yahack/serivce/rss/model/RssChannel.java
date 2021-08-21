package com.belkin.yahack.serivce.rss.model;

import java.util.List;

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
public class RssChannel {

    private String title;
    private String description;
    private String generator;
    private String link;
    private RssImage image;
    private List<RssEpisode> episodes;

}