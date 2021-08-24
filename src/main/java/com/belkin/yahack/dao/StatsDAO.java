package com.belkin.yahack.dao;

import java.util.List;
import java.util.Optional;

import com.belkin.yahack.model.stats.StatisticRecord;
import org.springframework.data.repository.CrudRepository;

public interface StatsDAO extends CrudRepository<StatisticRecord, Long> {

    List<StatisticRecord> findAllByElementId(String pollId);

    Optional<StatisticRecord> findByElementIdAndUsername(String elementId, String username);

    boolean existsByElementIdAndUsername(String id, String listenerUsername);

}
