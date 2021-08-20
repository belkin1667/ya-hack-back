package com.belkin.yahack.api.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class EpisodeMetadataResponse {

    //todo уточнить у фронтов что им нужно в превью и в фулл
    public EpisodeMetadataResponse(String podcastId, Integer id, String title, Integer duration) {
        this.podcastId = podcastId;
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    private String podcastId;
    private Integer id;
    private String title;
    private Integer duration;

    private Integer length;
    private String url;
    private String description;
    private List<InteractiveItemResponse> items;

    private boolean published;
}
