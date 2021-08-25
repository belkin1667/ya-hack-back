package com.belkin.yahack.serivce;

import com.belkin.yahack.api.dto.response.EpisodeMetadataResponse;
import com.belkin.yahack.api.dto.response.InteractiveImageButtonResponse;
import com.belkin.yahack.api.dto.response.InteractiveItemResponse;
import com.belkin.yahack.api.dto.response.InteractivePollResponse;
import com.belkin.yahack.api.dto.response.InteractiveTextResponse;
import com.belkin.yahack.api.dto.response.PodcastMetadataResponseWithEpisodes;
import com.belkin.yahack.dao.EpisodeDAO;
import com.belkin.yahack.dao.InteractiveItemDAO;
import com.belkin.yahack.dao.PodcastDAO;
import com.belkin.yahack.dao.StatsDAO;
import com.belkin.yahack.exception.not_found.EpisodeNotFoundException;
import com.belkin.yahack.exception.not_found.ItemNotFoundException;
import com.belkin.yahack.exception.not_found.PodcastNotFoundException;
import com.belkin.yahack.model.Episode;
import com.belkin.yahack.model.InteractiveImageButton;
import com.belkin.yahack.model.InteractiveItem;
import com.belkin.yahack.model.InteractivePoll;
import com.belkin.yahack.model.InteractiveText;
import com.belkin.yahack.model.Podcast;
import com.belkin.yahack.model.stats.PollAnswers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ListenService {

    private final PodcastDAO podcastDao;
    private final EpisodeDAO episodeDao;
    private final StatisticsService statisticsService;

    public List<String> getPodcastIdList() {
        List<String> podcastIds = new ArrayList<>();
        podcastDao.findAll().forEach(podcast -> podcastIds.add(podcast.getId()));
        return podcastIds;
    }


    public PodcastMetadataResponseWithEpisodes getPodcastPreview(String podcastId) {
        Optional<Podcast> maybePodcast = podcastDao.findById(podcastId);
        if (maybePodcast.isPresent())
            return new PodcastMetadataResponseWithEpisodes(maybePodcast.get().getId(),
                    maybePodcast.get().getTitle(),
                    maybePodcast.get().getImageUrl());
        throw new PodcastNotFoundException(podcastId);
    }

    public PodcastMetadataResponseWithEpisodes getPodcast(String podcastId) {
        Optional<Podcast> maybePodcast = podcastDao.findById(podcastId);
        if (maybePodcast.isPresent())
            return new PodcastMetadataResponseWithEpisodes(maybePodcast.get());
        throw new PodcastNotFoundException(podcastId);
    }

    public List<String> getEpisodesIds(String podcastId) {
        return episodeDao.findByPodcastIdAndPublished(podcastId, true).stream()
                .map(Episode::getGuid).collect(Collectors.toList());
    }

    public EpisodeMetadataResponse getEpisodePreview(String podcastId, Integer episodeNumber) {
        Optional<Episode> maybeEpisode = episodeDao.findByPodcastIdAndEpisodeNumberAndPublished(podcastId, episodeNumber, true);

        return getEpisodePreview(maybeEpisode);
    }

    public EpisodeMetadataResponse getEpisode(String podcastId, Integer episodeNumber) {
        Optional<Episode> maybeEpisode = episodeDao.findByPodcastIdAndEpisodeNumberAndPublished(podcastId, episodeNumber, true);
        return getEpisode(maybeEpisode);
    }

    public EpisodeMetadataResponse getEpisodePreview(String episodeId) {
        Optional<Episode> maybeEpisode = episodeDao.findByGuidAndPublished(episodeId, true);
        return getEpisodePreview(maybeEpisode);
    }

    public EpisodeMetadataResponse getEpisode(String episodeId, String listenerUsername) {
        Episode episode = episodeDao.findByGuidAndPublished(episodeId, true)
                .orElseThrow(() -> new EpisodeNotFoundException(episodeId));
        EpisodeMetadataResponse episodeResponse = new EpisodeMetadataResponse(episode);
        episodeResponse.setItems(getInteractiveItems(episode.getItems(), listenerUsername));
        return episodeResponse;
    }

    private Set<InteractiveItemResponse> getInteractiveItems(Set<InteractiveItem> items, String listenerUsername) {
        return items.stream().map(item -> {
            if (item instanceof InteractivePoll) {
                InteractivePoll poll = (InteractivePoll) item;
                boolean isAnswered = statisticsService.existsByElementIdAndUsername(poll.getId(), listenerUsername);
                if (isAnswered) {
                    return statisticsService.getPollResults(poll.getId());
                }
                else {
                    return new InteractivePollResponse(poll);
                }
            }
            else if (item instanceof InteractiveImageButton) {
                return new InteractiveImageButtonResponse((InteractiveImageButton) item);
            }
            else if (item instanceof InteractiveText){
                return new InteractiveTextResponse((InteractiveText) item);
            }
            else {
                return null;
            }
        }).collect(Collectors.toSet());
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
