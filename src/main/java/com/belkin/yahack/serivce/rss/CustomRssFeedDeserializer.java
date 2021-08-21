package com.belkin.yahack.serivce.rss;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import com.belkin.yahack.serivce.rss.model.RssChannel;
import com.belkin.yahack.serivce.rss.model.RssEpisode;
import com.belkin.yahack.serivce.rss.model.RssFeed;
import com.belkin.yahack.serivce.rss.model.RssImage;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomRssFeedDeserializer extends StdDeserializer<RssFeed> {

    public CustomRssFeedDeserializer() {
        this(null);
    }

    public CustomRssFeedDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public RssFeed deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        RssFeed rssFeed = new RssFeed();
        RssChannel channel = new RssChannel();
        RssImage image = new RssImage();

        ObjectCodec codec = parser.getCodec();
        JsonNode rssNode = codec.readTree(parser);
        JsonNode channelNode = rssNode.get("channel");

        channel.setTitle(cleanTags(channelNode.get("title").asText()));
        channel.setDescription(cleanTags(channelNode.get("description").asText()));
        channel.setLink(channelNode.get("link").get(0).asText());
        channel.setGenerator(channelNode.get("generator").asText());

        JsonNode imageNode = channelNode.get("image").get(0);
        image.setUrl(imageNode.get("url").asText());
        image.setTitle(cleanTags(imageNode.get("title").asText()));
        image.setLink(imageNode.get("link").asText());
        channel.setImage(image);
        rssFeed.setChannel(channel);

        if (!channelNode.has("item")) {
            return rssFeed;
        }
        if (channelNode.get("item").has("title")) {
            RssEpisode episode = parseEpisode(channelNode.get("item"));
            episode.setEpisodeNumber(0);
            channel.setEpisodes(List.of(episode));
        }
        else {
            List<RssEpisode> episodes = new ArrayList<>();
            JsonNode items = channelNode.get("item");
            for (int i = 0; i < items.size(); i++) {
                RssEpisode episode = parseEpisode(items.get(i));
                episode.setEpisodeNumber(i);
                episodes.add(episode);
            }
            channel.setEpisodes(episodes);
        }

        return rssFeed;
    }


    private RssEpisode parseEpisode(JsonNode episodeNode) {
        RssEpisode episode = new RssEpisode();
        episode.setTitle(cleanTags(episodeNode.get("title").asText()));
        episode.setDescription(cleanTags(episodeNode.get("description").asText()));
        episode.setLink(episodeNode.get("link").asText());
        episode.setPubDate(episodeNode.get("pubDate").asText());
        JsonNode enclosureNode = episodeNode.get("enclosure");
        episode.setAudioUrl(enclosureNode.get("url").asText());
        episode.setAudioType(enclosureNode.get("type").asText());
        episode.setAudioLength(enclosureNode.get("length").asLong());
        episode.setSummary(cleanTags(episodeNode.get("summary").asText()));
        episode.setExplicit(episodeNode.get("explicit").asText());
        episode.setDuration(episodeNode.get("duration").asLong());
        episode.setImageUrl(episodeNode.get("image").get("href").asText());
        episode.setSeason(episodeNode.get("season").asInt());
        episode.setGuid(episodeNode.get("guid").get("").asText());
        return episode;
    }


    private String cleanTags(String str) {
        return str.replaceAll("\\<.*?\\>", "");
    }
}