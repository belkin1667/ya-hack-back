package com.belkin.yahack.serivce.rss.job;

import com.belkin.yahack.dao.PodcastDAO;
import com.belkin.yahack.model.Podcast;
import com.belkin.yahack.serivce.PodcastManagementService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@NoArgsConstructor
@Slf4j
public class UpdateRssJob implements Job {

    private PodcastManagementService podcastManagementService;

    private PodcastDAO podcastDAO;

    @Autowired
    public void setPodcastDAO(PodcastDAO podcastDAO) {
        this.podcastDAO = podcastDAO;
    }

    @Autowired
    public void setPodcastManagementService(PodcastManagementService podcastManagementService) {
        this.podcastManagementService = podcastManagementService;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("Updating podcasts job started");
        List<Podcast> podcasts = StreamSupport.stream(podcastDAO.findAll().spliterator(), false).collect(Collectors.toList());

        for (Podcast podcast :
                podcasts) {
            podcastManagementService.updatePodcast(podcast);
        }
        log.info("Updating podcasts job finished");
    }
}
