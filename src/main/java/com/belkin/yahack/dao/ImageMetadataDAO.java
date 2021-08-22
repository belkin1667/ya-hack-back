package com.belkin.yahack.dao;

import com.belkin.yahack.model.ImageMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageMetadataDAO extends CrudRepository<ImageMetadata, String> {
}
