package com.belkin.yahack.dao;

import java.util.List;
import java.util.Optional;

import com.belkin.yahack.model.Podcast;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PodcastDAO extends CrudRepository<Podcast, String> {

    List<Podcast> findAllByAuthor(String author);

    Optional<Podcast> findByIdAndAuthor(String podcastId, String username);

    boolean existsByRss(String rss);

    boolean existsByIdAndAuthor(String podcastId, String username);
}
