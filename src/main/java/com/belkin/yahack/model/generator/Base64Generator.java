package com.belkin.yahack.model.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

/**
 * Generates base64 ID of type {@link String}
 */
public class Base64Generator implements IdentifierGenerator {

    //TODO: add unique id check
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
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