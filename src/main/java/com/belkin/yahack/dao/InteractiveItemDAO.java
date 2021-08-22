package com.belkin.yahack.dao;

import com.belkin.yahack.model.InteractiveItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractiveItemDAO extends CrudRepository<InteractiveItem, String> {

}
