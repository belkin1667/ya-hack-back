package com.belkin.yahack.model.generator;

import com.belkin.yahack.dao.EpisodeDAO;
import com.belkin.yahack.dao.ImageMetadataDAO;
import com.belkin.yahack.dao.PodcastDAO;
import com.belkin.yahack.model.Episode;
import com.belkin.yahack.model.Podcast;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * Generates base64 ID of type {@link String}
 */
@RequiredArgsConstructor
public class Base64Generator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String query = String.format("SELECT %s FROM %s",
                session.getEntityPersister(object.getClass().getName(), object).getIdentifierPropertyName(),
                object.getClass().getSimpleName());
        Stream ids = session.createQuery(query).stream();

        while(true) {
            String id = getRandomBase64();
            if (!ids.anyMatch(id::equals)) {
                return id;
            }
        }
    }


    private String getRandomBase64() {
        // Create random UUID
        UUID uuid = UUID.randomUUID();

        // Create byte[] for base64 from uuid
        byte[] src = ByteBuffer.wrap(new byte[16])
                .putLong(uuid.getMostSignificantBits())
                .putLong(uuid.getLeastSignificantBits())
                .array();

        // Encode to Base64 and remove trailing ==
        return Base64.getUrlEncoder().encodeToString(src).substring(0, 22);
    }
}