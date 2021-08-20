package com.belkin.yahack.dao;

import com.belkin.yahack.model.Podcast;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PodcastDAO extends CrudRepository<Podcast, String> {

}
