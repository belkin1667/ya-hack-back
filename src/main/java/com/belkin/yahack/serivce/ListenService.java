package com.belkin.yahack.serivce;

import com.belkin.yahack.api.dto.response.EpisodeMetadataResponse;
import com.belkin.yahack.api.dto.response.PodcastMetadataResponse;
import com.belkin.yahack.dao.EpisodeDAO;
import com.belkin.yahack.dao.PodcastDAO;
import com.belkin.yahack.exception.not_found.EpisodeNotFoundException;
import com.belkin.yahack.exception.not_found.PodcastNotFoundException;
import com.belkin.yahack.model.Episode;
import com.belkin.yahack.model.Podcast;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ListenService {

    private final PodcastDAO podcastDao;
    private final EpisodeDAO episodeDao;

    public List<String> getPodcastIdList() {
        List<String> podcastIds = new ArrayList<>();
        podcastDao.findAll().forEach(podcast -> podcastIds.add(podcast.getId()));
        return podcastIds;
    }


    public PodcastMetadataResponse getPodcastPreview(String podcastId) {
        Optional<Podcast> maybePodcast = podcastDao.findById(podcastId);
        if (maybePodcast.isPresent())
            return new PodcastMetadataResponse(maybePodcast.get().getId(),
                    maybePodcast.get().getTitle(),
                    maybePodcast.get().getImageUrl());
        throw new PodcastNotFoundException(podcastId);
    }

    public PodcastMetadataResponse getPodcast(String podcastId) {
        Optional<Podcast> maybePodcast = podcastDao.findById(podcastId);
        if (maybePodcast.isPresent())
            return new PodcastMetadataResponse(maybePodcast.get());
        throw new PodcastNotFoundException(podcastId);
    }

    public List<String> getEpisodesIds(String podcastId) {
        return episodeDao.findByPodcastIdAndPublished(podcastId, true);
    }

    public EpisodeMetadataResponse getEpisodePreview(String podcastId, Integer episodeNumber) {
        Optional<Episode> maybeEpisode = episodeDao.findByPodcastIdAndEpisodeNumber(podcastId, episodeNumber);
        return getEpisodePreview(maybeEpisode);
    }

    public EpisodeMetadataResponse getEpisode(String podcastId, Integer episodeNumber) {
        Optional<Episode> maybeEpisode = episodeDao.findByPodcastIdAndEpisodeNumber(podcastId, episodeNumber);
        return getEpisode(maybeEpisode);
    }

    public EpisodeMetadataResponse getEpisodePreview(String episodeId) {
        Optional<Episode> maybeEpisode = episodeDao.findById(episodeId);
        return getEpisodePreview(maybeEpisode);
    }

    public EpisodeMetadataResponse getEpisode(String episodeId) {
        Optional<Episode> maybeEpisode = episodeDao.findById(episodeId);
        return getEpisode(maybeEpisode);
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
