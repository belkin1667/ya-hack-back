package com.belkin.yahack.serivce;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.belkin.yahack.model.Episode;
import com.belkin.yahack.model.Podcast;
import com.rometools.modules.itunes.EntryInformationImpl;
import com.sun.syndication.feed.module.ModuleImpl;
import com.sun.syndication.feed.module.SyModuleImpl;
import com.sun.syndication.feed.synd.SyndEnclosure;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.springframework.stereotype.Component;

@Component
public class RssUpdater {

    public void update(Podcast podcast)  { // WIP
        try {
            URL feedSource = new URL(podcast.getRss());
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedSource));

            podcast.setLink(feed.getLink());
            podcast.setImageUrl(feed.getImage().getUrl());

            List entries = feed.getEntries();
            for (int i = 0; i < entries.size(); i++) {
                Episode episode = new Episode(i);
                SyndEntry entry = (SyndEntry) entries.get(i);

                SyndEnclosure enclosure = (SyndEnclosure) entry.getEnclosures().get(0);
                EntryInformationImpl module = (EntryInformationImpl) entry.getModules().get(0);

                episode.setUrl(enclosure.getUrl());
                episode.setLength(enclosure.getLength());
                //episode.setPubDate();
                episode.setDuration(module.getDuration().getMilliseconds());

                for (Object m : entry.getModules()) {
                    System.out.println(((ModuleImpl) m).getClass());
                }
            }
            //podcast.setEpisodes();

            podcast.setLastUpdatedTime(new Date().toString());
        } catch (IOException | FeedException e) {
            System.out.println("Exception: " + e.getMessage());

            System.out.println(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n")));
        }
    }
}
