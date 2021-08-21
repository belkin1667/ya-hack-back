package com.belkin.yahack.dao;

import com.belkin.yahack.model.Episode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeDAO extends CrudRepository<Episode, String> {

}
