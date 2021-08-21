package com.belkin.yahack.serivce;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.belkin.yahack.api.dto.request.PodcastCreationRequest;
import com.belkin.yahack.api.dto.response.PodcastMetadataResponse;
import com.belkin.yahack.dao.PodcastDAO;
import com.belkin.yahack.exception.access_denied.AccessDeniedException;
import com.belkin.yahack.exception.already_exists.PodcastAlreadyExistsException;
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
        if (podcastDAO.existsByRss(podcastCreationRequest.getRss()))
            throw new PodcastAlreadyExistsException("rss", podcastCreationRequest.getRss());
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

    public PodcastMetadataResponse getPodcastPreview(String podcastId, String username) {
        if (isAuthor(podcastId, username)) {
            Podcast podcast = podcastDAO.findById(podcastId).get();
            return new PodcastMetadataResponse(podcastId, podcast.getTitle(), podcast.getImageUrl());
        }

        throw new AccessDeniedException(String.format("User %s is not an author of podcast %s", username, podcastId));
    }

    public PodcastMetadataResponse getPodcast(String podcastId, String username) {
        if (isAuthor(podcastId, username)) {
            Podcast podcast = podcastDAO.findById(podcastId).get();
            return new PodcastMetadataResponse(podcastId, podcast.getTitle(), podcast.getImageUrl());
        }

        throw new AccessDeniedException(String.format("User %s is not an author of podcast %s", username, podcastId));
    }


    private boolean isAuthor(String podcastId, String username) {
        return podcastDAO.existsByIdAndAuthor(podcastId, username);
    }
}
