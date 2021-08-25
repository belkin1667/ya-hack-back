package com.belkin.yahack.serivce.rss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.belkin.yahack.model.Episode;
import com.belkin.yahack.model.Podcast;


import com.belkin.yahack.serivce.PodcastManagementService;
import com.belkin.yahack.serivce.rss.model.RssChannel;
import com.belkin.yahack.serivce.rss.model.RssEpisode;
import com.belkin.yahack.serivce.rss.model.RssFeed;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RssUpdater {

    private final XmlMapper xmlMapper;

    public List<Episode> update(Podcast podcast)  {
        try {
            String xml = fetchXmlFeed(podcast.getRss());
            RssFeed feed = xmlMapper.readValue(xml, RssFeed.class);
            return update(podcast, feed.getChannel());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            System.out.println(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n")));
        }
        return new ArrayList<>();
    }

    private List<Episode> update(Podcast podcast, RssChannel feed) {
        podcast.setImageUrl(feed.getImage().getUrl());
        podcast.setLink(feed.getLink());
        Set<Episode> episodes = podcast.getEpisodes();
        List<Episode> newEpisodes = new ArrayList<>();
        List<RssEpisode> rssEpisodes = feed.getEpisodes();
        if (rssEpisodes == null || rssEpisodes.size() == 0)
            return new ArrayList<>();
        for (RssEpisode rssEpisode : rssEpisodes) {
            Optional<Episode> maybeEpisode = episodes.stream().filter(e -> e.getGuid().equals(rssEpisode.getGuid())).findFirst();
            if (maybeEpisode.isEmpty()) {
                Episode episode = getEpisode(rssEpisode);
                episode.setPodcast(podcast);
                newEpisodes.add(episode);
            }
        }
        podcast.setLastUpdatedTime(new Date().toString());
        return newEpisodes;
    }

    private Episode getEpisode(RssEpisode rssEpisode) {
        Episode episode = new Episode();
        episode.setGuid(rssEpisode.getGuid());
        episode.setEpisodeNumber(rssEpisode.getEpisodeNumber());
        episode.setPubDate(rssEpisode.getPubDate());
        episode.setDescription(rssEpisode.getDescription());
        episode.setDuration(rssEpisode.getDuration());
        episode.setLength(rssEpisode.getAudioLength());
        episode.setTitle(rssEpisode.getTitle());
        episode.setUrl(rssEpisode.getAudioUrl());
        return episode;
    }

    private String fetchXmlFeed(String url) throws IOException {
        System.out.println("Fetching RSS: " + url);
        HttpUriRequest request = new HttpGet(url);
        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            try (CloseableHttpResponse response = client.execute(request);
                 BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                return br.lines().collect(Collectors.joining("\n"));
            }
        }
    }

}

