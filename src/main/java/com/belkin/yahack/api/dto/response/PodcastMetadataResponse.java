package com.belkin.yahack.api.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@AllArgsConstructor
public class PodcastMetadataResponse {

    //todo уточнить у фронтов что им нужно в превью и в фулл
    public PodcastMetadataResponse(String id, String title, String imageUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    private String id;
    private String title;
    private String imageUrl;

    private String description;
    private List<Integer> episodeIds;
}
