package com.belkin.yahack.serivce;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.belkin.yahack.api.dto.request.PodcastCreationRequest;
import com.belkin.yahack.dao.PodcastDAO;
import com.belkin.yahack.model.Podcast;
import com.belkin.yahack.serivce.rss.RssUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PodcastManagementService {

    private final PodcastDAO podcastDAO;
    private final RssUpdater rssUpdater;

    public void addPodcast(PodcastCreationRequest podcastCreationRequest, String author) {
        Podcast podcast = new Podcast(podcastCreationRequest, author);
        rssUpdater.update(podcast);
        podcastDAO.save(podcast);
    }

    public List<Podcast> getAllPodcasts() {
        return StreamSupport.stream(podcastDAO.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public List<String> getMyPodcastsIds(String author) {
        return podcastDAO.findAllByAuthor(author).stream().map(Podcast::getId).collect(Collectors.toList());
    }
}
