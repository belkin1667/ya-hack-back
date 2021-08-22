package com.belkin.yahack.dao;

import java.util.List;
import java.util.Optional;

import com.belkin.yahack.model.Episode;
import com.belkin.yahack.model.Podcast;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeDAO extends CrudRepository<Episode, String> {

    Optional<Episode> findByGuidAndPublished(String guid, boolean published);

    List<Episode> findByPodcastIdAndPublished(String podcastId, boolean b);

    Optional<Episode> findByPodcastIdAndEpisodeNumber(String podcastId, Integer episodeNumber);

    Optional<Episode> findByPodcastIdAndEpisodeNumberAndPublished(String podcastId, Integer episodeNumber, boolean published);

    List<Episode> findAllByPodcastId(String podcastId);

    List<Episode> findAllByPodcast(Podcast podcast);
}
