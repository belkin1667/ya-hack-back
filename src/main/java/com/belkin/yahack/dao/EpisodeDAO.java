package com.belkin.yahack.dao;

import java.util.List;
import java.util.Optional;

import com.belkin.yahack.model.Episode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeDAO extends CrudRepository<Episode, String> {

    List<String> findByPodcastIdAndPublished(String podcastId, boolean b);

    Optional<Episode> findByPodcastIdAndEpisodeNumber(String podcastId, Integer episodeNumber);
}
