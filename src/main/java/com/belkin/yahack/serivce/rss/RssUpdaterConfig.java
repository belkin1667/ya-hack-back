package com.belkin.yahack.serivce.rss;

import com.belkin.yahack.serivce.rss.model.RssFeed;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RssUpdaterConfig {

    @Bean
    public XmlMapper xmlMapper() {
        XmlMapper mapper = new XmlMapper();
        SimpleModule module =
                new SimpleModule("CustomRssFeedDeserializer", new Version(1, 0, 0, null, null, null));
        module.addDeserializer(RssFeed.class, new CustomRssFeedDeserializer());
        mapper.registerModule(module);

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

}
