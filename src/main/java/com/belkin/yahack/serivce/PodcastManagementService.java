package com.belkin.yahack.serivce;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.belkin.yahack.api.dto.request.PodcastCreationRequest;
import com.belkin.yahack.api.dto.response.EpisodeMetadataResponse;
import com.belkin.yahack.api.dto.response.PodcastMetadataResponse;
import com.belkin.yahack.dao.EpisodeDAO;
import com.belkin.yahack.dao.PodcastDAO;
import com.belkin.yahack.exception.access_denied.AccessDeniedException;
import com.belkin.yahack.exception.already_exists.PodcastAlreadyExistsException;
import com.belkin.yahack.exception.not_found.EpisodeNotFoundException;
import com.belkin.yahack.model.Episode;
import com.belkin.yahack.model.Podcast;
import com.belkin.yahack.serivce.rss.RssUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PodcastManagementService {

    private final PodcastDAO podcastDAO;
    private final EpisodeDAO episodeDAO;
    private final RssUpdater rssUpdater;

    public Podcast addPodcast(PodcastCreationRequest podcastCreationRequest, String author) {
        if (podcastDAO.existsByRss(podcastCreationRequest.getRss()))
            throw new PodcastAlreadyExistsException("rss", podcastCreationRequest.getRss());
        Podcast podcast = new Podcast(podcastCreationRequest, author);
        List<Episode> newEpisodes = rssUpdater.update(podcast);
        podcastDAO.save(podcast);
        addEpisodes(newEpisodes);
        return podcast;
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

    public void addEpisodes(List<Episode> episodes) {
        episodeDAO.saveAll(episodes);
    }

    public List<String> getEpisodesIds(String podcastId, String username) {
        if (isAuthor(podcastId, username)) {
            return episodeDAO.findAllByPodcastId(podcastId).stream()
                    .map(Episode::getGuid).collect(Collectors.toList());
        }

        throw new AccessDeniedException(String.format("User %s is not an author of podcast %s", username, podcastId));
    }

    public EpisodeMetadataResponse getEpisodePreview(String podcastId, Integer episodeNumber, String username) {
        if (isAuthor(podcastId, username)) {
            Optional<Episode> maybeEpisode = episodeDAO.findByPodcastIdAndEpisodeNumber(podcastId, episodeNumber);
            return getEpisodePreview(maybeEpisode);
        }

        throw new AccessDeniedException(String.format("User %s is not an author of podcast %s", username, podcastId));
    }

    public EpisodeMetadataResponse getEpisode(String podcastId, Integer episodeNumber, String username) {
        if (isAuthor(podcastId, username)) {
            Optional<Episode> maybeEpisode = episodeDAO.findByPodcastIdAndEpisodeNumber(podcastId, episodeNumber);
            return getEpisode(maybeEpisode);
        }

        throw new AccessDeniedException(String.format("User %s is not an author of podcast %s", username, podcastId));
    }

    public void publishEpisode(String podcastId, Integer episodeNumber, String username) {
        if (!isAuthor(podcastId, username)) {
            throw new AccessDeniedException(String.format("User %s is not an author of podcast %s", username, podcastId));
        }

        Optional<Episode> maybeEpisode = episodeDAO.findByPodcastIdAndEpisodeNumber(podcastId, episodeNumber);
        if (maybeEpisode.isEmpty()) {
            throw new EpisodeNotFoundException();
        }
        Episode episode = maybeEpisode.get();
        episode.setPublished(true);

        episodeDAO.save(episode);
    }


    private EpisodeMetadataResponse getEpisodePreview(Optional<Episode> maybeEpisode) {
        if (maybeEpisode.isPresent())
            return new EpisodeMetadataResponse(maybeEpisode.get().getGuid(),
                    maybeEpisode.get().getPodcast().getId(),
                    maybeEpisode.get().getEpisodeNumber(),
                    maybeEpisode.get().getTitle(),
                    maybeEpisode.get().getDuration());
        throw new EpisodeNotFoundException();
    }

    private EpisodeMetadataResponse getEpisode(Optional<Episode> maybeEpisode) {
        if (maybeEpisode.isPresent())
            return new EpisodeMetadataResponse(maybeEpisode.get());
        throw new EpisodeNotFoundException();
    }
}
