package com.belkin.yahack.serivce;

import com.belkin.yahack.dao.PodcastDAO;
import com.belkin.yahack.model.Podcast;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ListenService {

    private final PodcastDAO podcastDao;

    public List<String> getPodcastIdList() {
        List<String> podcastIds = new ArrayList<>();
        podcastDao.findAll().forEach(podcast -> podcastIds.add(podcast.getId().toString()));
        return podcastIds;
    }

}
