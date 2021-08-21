package com.belkin.yahack;

import com.belkin.yahack.api.dto.request.PodcastCreationRequest;
import com.belkin.yahack.security.ApplicationUserRole;
import com.belkin.yahack.security.dto.RegistrationRequest;
import com.belkin.yahack.security.service.ApplicationUserService;
import com.belkin.yahack.serivce.PodcastManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class StartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        generateMockUsers();
        generateMockPodcasts();
    }

    private final ApplicationUserService applicationUserService;
    private final PodcastManagementService podcastManagementService;

    private void generateMockUsers() {
        log.info("Creating user 'user'");
        RegistrationRequest user = new RegistrationRequest();
        user.setUsername("user");
        user.setPassword("pass");
        applicationUserService.register(user, ApplicationUserRole.USER);

        log.info("Creating user 'user2'");
        user = new RegistrationRequest();
        user.setUsername("user2");
        user.setPassword("pass");
        applicationUserService.register(user, ApplicationUserRole.USER);

        log.info("Creating user 'author'");
        RegistrationRequest author = new RegistrationRequest();
        author.setUsername("author");
        author.setPassword("pass");
        applicationUserService.register(author, ApplicationUserRole.AUTHOR);

        log.info("Creating user 'author2'");
        author = new RegistrationRequest();
        author.setUsername("author2");
        author.setPassword("pass");
        applicationUserService.register(author, ApplicationUserRole.AUTHOR);
    }

    private void generateMockPodcasts() {
        log.info("Creating podcast 'Test podcast 1' as 'author'");
        PodcastCreationRequest podcast = new PodcastCreationRequest("https://anchor.fm/s/2ea5680/podcast/rss", "Test podcast 1", "Very cool test podcast 1");
        podcastManagementService.addPodcast(podcast, "author");

        log.info("Creating podcast 'Test podcast 2' as 'author2'");
        podcast = new PodcastCreationRequest("https://anchor.fm/s/67cae600/podcast/rss", "Test podcast 2", "Very cool test podcast 2");
        podcastManagementService.addPodcast(podcast, "author2");
    }
}