package com.belkin.yahack;

import java.util.List;

import com.belkin.yahack.api.dto.request.InteractiveImageButtonRequest;
import com.belkin.yahack.api.dto.request.InteractiveItemRequest;
import com.belkin.yahack.api.dto.request.InteractivePollRequest;
import com.belkin.yahack.api.dto.request.PodcastCreationRequest;
import com.belkin.yahack.model.Podcast;
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
        Podcast generatedPodcast = podcastManagementService.addPodcast(podcast, "author");
        log.info("Podcast id=" + generatedPodcast.getId());




        log.info("Creating podcast 'Test podcast 2' as 'author2'");
        podcast = new PodcastCreationRequest("https://anchor.fm/s/67cae600/podcast/rss", "Test podcast 2", "Very cool test podcast 2");
        generatedPodcast = podcastManagementService.addPodcast(podcast, "author2");
        log.info("Podcast id=" + generatedPodcast.getId());

        String guid = "a7f026cf-3d15-4a5a-921c-fa65281c8e1a";
        podcastManagementService.addInteractiveItem(guid, getImageButton());
        podcastManagementService.addInteractiveItem(guid, getButton());
        podcastManagementService.addInteractiveItem(guid, getImage());
        podcastManagementService.addInteractiveItem(guid, getPoll(true, true, 15));
        podcastManagementService.addInteractiveItem(guid, getPoll(false, true, 25));
        podcastManagementService.addInteractiveItem(guid, getPoll(true, false, 35));
        podcastManagementService.addInteractiveItem(guid, getPoll(false, false, 45));
        log.info("Added items to 2nd episode of podcast");

        podcastManagementService.publishEpisode(guid, "author2");
        log.info("Published 2nd episode of podcast");
    }

    private InteractiveImageButtonRequest getImageButton() {
        InteractiveImageButtonRequest imagebutton = new InteractiveImageButtonRequest();
        imagebutton.setTimeStart(1);
        imagebutton.setTimeEnd(4);
        imagebutton.setButtonText("Купи скайрим");
        imagebutton.setButtonUrl("https://google.com");
        imagebutton.setImageUrl("https://d3t3ozftmdmh3i.cloudfront.net/production/podcast_uploaded_nologo/17313504/17313504-1629575611498-dd44d1801c0dc.jpg");
        return imagebutton;
    }

    private InteractiveImageButtonRequest getImage() {
        InteractiveImageButtonRequest imagebutton = new InteractiveImageButtonRequest();
        imagebutton.setTimeStart(5);
        imagebutton.setTimeEnd(9);
        imagebutton.setButtonText(null);
        imagebutton.setButtonUrl(null);
        imagebutton.setImageUrl("https://d3t3ozftmdmh3i.cloudfront.net/production/podcast_uploaded_nologo/17313504/17313504-1629575611498-dd44d1801c0dc.jpg");
        return imagebutton;
    }

    private InteractiveImageButtonRequest getButton() {
        InteractiveImageButtonRequest imagebutton = new InteractiveImageButtonRequest();
        imagebutton.setTimeStart(10);
        imagebutton.setTimeEnd(14);
        imagebutton.setButtonText(null);
        imagebutton.setButtonUrl(null);
        imagebutton.setImageUrl("https://d3t3ozftmdmh3i.cloudfront.net/production/podcast_uploaded_nologo/17313504/17313504-1629575611498-dd44d1801c0dc.jpg");
        return imagebutton;
    }

    private InteractivePollRequest getPoll(boolean multipleOptions, boolean hasCorrect, int timeStart) {
        InteractivePollRequest poll = new InteractivePollRequest();
        poll.setTimeStart(timeStart);
        poll.setTimeEnd(timeStart + 5);
        poll.setQuestion("Купил скайрим?");
        poll.setOptions(List.of("Да", "Нет", "Наверное"));
        poll.setMultipleOptions(multipleOptions);
        if (hasCorrect)
            poll.setCorrectAnswers(List.of(0));
        else
            poll.setCorrectAnswers(null);
        return poll;
    }
}