package com.belkin.yahack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.belkin.yahack.api.dto.request.InteractiveImageButtonRequest;
import com.belkin.yahack.api.dto.request.InteractivePollRequest;
import com.belkin.yahack.api.dto.request.InteractiveTextRequest;
import com.belkin.yahack.api.dto.request.PodcastCreationRequest;
import com.belkin.yahack.api.dto.response.InteractiveItemResponse;
import com.belkin.yahack.api.dto.response.InteractivePollResponse;
import com.belkin.yahack.model.InteractiveItem;
import com.belkin.yahack.model.ItemType;
import com.belkin.yahack.model.Podcast;
import com.belkin.yahack.security.ApplicationUserRole;
import com.belkin.yahack.security.dto.RegistrationRequest;
import com.belkin.yahack.security.service.ApplicationUserService;
import com.belkin.yahack.serivce.ListenService;
import com.belkin.yahack.serivce.PodcastManagementService;
import com.belkin.yahack.serivce.StatisticsService;
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
        generateMockPollAnswers("e3b52afe-603d-4cba-b2f3-f27d1fd5fce2");
        generateMockPollAnswers("3e514e86-dfcf-4097-9a7a-7794d2f12e56");
    }

    private final ApplicationUserService applicationUserService;
    private final PodcastManagementService podcastManagementService;
    private final StatisticsService statisticsService;
    private final ListenService listenService;

    private void generateMockUsers() {
        log.info("Creating user 'user'");
        RegistrationRequest user = new RegistrationRequest();
        user.setUsername("user");
        user.setPassword("pass");
        applicationUserService.register(user, ApplicationUserRole.USER);

        for (int i = 0; i < 15; i++) {
            log.info("Creating user 'user" + i + "'");
            user = new RegistrationRequest();
            user.setUsername("user" + i);
            user.setPassword("pass");
            applicationUserService.register(user, ApplicationUserRole.USER);
        }

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

        log.info("Creating user 'author3'");
        author = new RegistrationRequest();
        author.setUsername("author3");
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

        String guid = "27f55048-6117-4e99-8b7a-b69d2945670d";
        log.info("Added items to 1st episode of podcast");
        podcastManagementService.publishEpisode(guid, "author2");

        guid = "a7f026cf-3d15-4a5a-921c-fa65281c8e1a";
        log.info("Added items to 2nd episode of podcast");
        podcastManagementService.publishEpisode(guid, "author2");

        guid = "37803bd1-141e-4c9f-9ea8-31ab3dfba0ff";
        podcastManagementService.publishEpisode(guid, "author2");
        log.info("Published 3rd episode of podcast");

        guid = "e3b52afe-603d-4cba-b2f3-f27d1fd5fce2";
        podcastManagementService.addInteractiveItem(guid, getImageButton());
        podcastManagementService.addInteractiveItem(guid, getButton());
        podcastManagementService.addInteractiveItem(guid, getImage());
        podcastManagementService.addInteractiveItem(guid, getPoll("Купил?", List.of("Да"), true, true, 35));
        podcastManagementService.addInteractiveItem(guid, getPoll("Купил тлоу?", List.of("Да", "Нет"),  false, true, 45));
        podcastManagementService.addInteractiveItem(guid, getPoll("Купил баттлфилд?", List.of("Да", "Нет", "Наверное"), true, false, 55));
        podcastManagementService.addInteractiveItem(guid, getPoll("Купил покушать?",  List.of("Да", "Нет", "Наверное", "Конечно!"), false, false, 65));
        podcastManagementService.addInteractiveItem(guid, getText());
        podcastManagementService.addInteractiveItem(guid, getForm());
        log.info("Added items to 4th episode of podcast");
        podcastManagementService.publishEpisode(guid, "author2");
        log.info("Published 4th episode of podcast");

        podcast = new PodcastCreationRequest("https://anchor.fm/s/690f4c18/podcast/rss",
                "Подкаст Лаборатории Медиасервисов",
                "Интервью с известными интеллектуалами о любимых книгах и не только");
        generatedPodcast = podcastManagementService.addPodcast(podcast, "author3");
        log.info("Podcast (for demo) id=" + generatedPodcast.getId());

        guid = "3e514e86-dfcf-4097-9a7a-7794d2f12e56";
        podcastManagementService.addInteractiveItem(guid, getImageDemo());
        podcastManagementService.addInteractiveItem(guid, getTextDemo());
        podcastManagementService.addInteractiveItem(guid, getPoll2Demo());
        podcastManagementService.addInteractiveItem(guid, getPoll3Demo());
        podcastManagementService.addInteractiveItem(guid, getPoll4Demo());
        podcastManagementService.addInteractiveItem(guid, getFormDemo());
        podcastManagementService.addInteractiveItem(guid, getButtonDemo());
        podcastManagementService.addInteractiveItem(guid, getImageButtonDemo());
        podcastManagementService.publishEpisode(guid, "author3");


    }

    private InteractivePollRequest getPoll4Demo() {
        InteractivePollRequest poll = new InteractivePollRequest();
        poll.setTimeStart(100);
        poll.setTimeEnd(110);
        poll.setQuestion("Какие книги вы читаете?");
        poll.setOptions(List.of("Художка", "Non-fiction", "Учебники", "Не читаю"));
        poll.setMultipleOptions(true);
        poll.setCorrectAnswers(null);
        return poll;
    }

    private InteractivePollRequest getPoll3Demo() {
        InteractivePollRequest poll = new InteractivePollRequest();
        poll.setTimeStart(75);
        poll.setTimeEnd(85);
        poll.setQuestion("А вы получаете вдохновение от классики?");
        poll.setOptions(List.of("Да", "Нет", "Не читаю классику"));
        poll.setMultipleOptions(false);
        poll.setCorrectAnswers(null);
        return poll;
    }

    private InteractivePollRequest getPoll2Demo() {
        InteractivePollRequest poll = new InteractivePollRequest();
        poll.setTimeStart(28);
        poll.setTimeEnd(38);
        poll.setQuestion("А вы стали больше читать?");
        poll.setOptions(List.of("Да", "Нет"));
        poll.setMultipleOptions(false);
        poll.setCorrectAnswers(List.of(0));
        return poll;
    }

    private InteractiveTextRequest getFormDemo() {
        InteractiveTextRequest text = new InteractiveTextRequest();
        text.setText("Ссылка на ваш инстаграм");
        text.setHasInputForm(true);
        text.setTimeStart(131);
        text.setTimeEnd(158);
        return text;
    }

    private InteractiveTextRequest getTextDemo() {
        InteractiveTextRequest text = new InteractiveTextRequest();
        text.setText("Леонид Парфёнов — журналист, телеведущий, писатель, автор YouTube-канала Parfenon и легендарного проекта «Намедни»");
        text.setHasInputForm(false);
        text.setTimeStart(9);
        text.setTimeEnd(20);
        return text;
    }

    private InteractiveImageButtonRequest getImageDemo() {
        InteractiveImageButtonRequest imagebutton = new InteractiveImageButtonRequest();
        imagebutton.setTimeStart(2);
        imagebutton.setTimeEnd(8);
        imagebutton.setButtonText(null);
        imagebutton.setButtonUrl(null);
        imagebutton.setImageUrl("https://yt3.ggpht.com/ytc/AKedOLTN-dPQZ97tYSerNX9lGzc6O-D8fu8RX6WZFTnW=s176-c-k-c0x00ffffff-no-rj");
        return imagebutton;
    }

    private InteractiveImageButtonRequest getButtonDemo() {
        InteractiveImageButtonRequest imagebutton = new InteractiveImageButtonRequest();
        imagebutton.setTimeStart(161);
        imagebutton.setTimeEnd(171);
        imagebutton.setButtonText("Мой инстаграм");
        imagebutton.setButtonUrl("https://www.instagram.com/mustreader/");
        imagebutton.setImageUrl(null);
        return imagebutton;
    }

    private InteractiveImageButtonRequest getImageButtonDemo() {
        InteractiveImageButtonRequest imagebutton = new InteractiveImageButtonRequest();
        imagebutton.setTimeStart(175);
        imagebutton.setTimeEnd(182);
        imagebutton.setButtonText("Смотреть на ютуб");
        imagebutton.setButtonUrl("https://www.youtube.com/watch?v=QG6Y_wWDJHE");
        imagebutton.setImageUrl("https://yt3.ggpht.com/ytc/AKedOLTN-dPQZ97tYSerNX9lGzc6O-D8fu8RX6WZFTnW=s176-c-k-c0x00ffffff-no-rj");
        return imagebutton;
    }


    private InteractiveImageButtonRequest getImageButton() {
        InteractiveImageButtonRequest imagebutton = new InteractiveImageButtonRequest();
        imagebutton.setTimeStart(5);
        imagebutton.setTimeEnd(10);
        imagebutton.setButtonText("Купи скайрим");
        imagebutton.setButtonUrl("https://google.com");
        imagebutton.setImageUrl("https://d3t3ozftmdmh3i.cloudfront.net/production/podcast_uploaded_nologo/17313504/17313504-1629575611498-dd44d1801c0dc.jpg");
        return imagebutton;
    }

    private InteractiveImageButtonRequest getImage() {
        InteractiveImageButtonRequest imagebutton = new InteractiveImageButtonRequest();
        imagebutton.setTimeStart(15);
        imagebutton.setTimeEnd(20);
        imagebutton.setButtonText(null);
        imagebutton.setButtonUrl(null);
        imagebutton.setImageUrl("https://d3t3ozftmdmh3i.cloudfront.net/production/podcast_uploaded_nologo/17313504/17313504-1629575611498-dd44d1801c0dc.jpg");
        return imagebutton;
    }

    private InteractiveImageButtonRequest getButton() {
        InteractiveImageButtonRequest imagebutton = new InteractiveImageButtonRequest();
        imagebutton.setTimeStart(25);
        imagebutton.setTimeEnd(30);
        imagebutton.setButtonText("Купи скайрим");
        imagebutton.setButtonUrl("https://google.com");
        imagebutton.setImageUrl(null);
        return imagebutton;
    }

    private InteractivePollRequest getPoll(String question, List<String> options, boolean multipleOptions, boolean hasCorrect, int timeStart) {
        InteractivePollRequest poll = new InteractivePollRequest();
        poll.setTimeStart(timeStart);
        poll.setTimeEnd(timeStart + 5);
        poll.setQuestion(question);
        poll.setOptions(options);
        poll.setMultipleOptions(multipleOptions);
        if (hasCorrect)
            poll.setCorrectAnswers(List.of(0));
        else
            poll.setCorrectAnswers(null);
        return poll;
    }


    private InteractiveTextRequest getText() {
        InteractiveTextRequest text = new InteractiveTextRequest();
        text.setText("Термин тут должен быть длинный очень длинный, особенно полезно когда подкаст " +
                "на какую-то очень умную тему, а ты тупой");
        text.setHasInputForm(false);
        text.setType(ItemType.text);
        text.setTimeStart(75);
        text.setTimeEnd(80);
        return text;
    }

    private InteractiveTextRequest getForm() {
        InteractiveTextRequest form = new InteractiveTextRequest();
        form.setText("Напиши как дела");
        form.setHasInputForm(true);
        form.setType(ItemType.text);
        form.setTimeStart(85);
        form.setTimeEnd(90);
        return form;
    }


    private static final Random rnd = new Random(0);

    private void generateMockPollAnswers(String episodeId) {
        List<String> pollIds = new ArrayList<>();
        List<Integer> pollSizes = new ArrayList<>();
        List<Boolean> pollTypes = new ArrayList<>();

        Set<InteractiveItemResponse> items = listenService.getEpisode(episodeId, "user3").getItems();
        for (InteractiveItemResponse item : items) {
            if (item instanceof InteractivePollResponse) {
                InteractivePollResponse poll = (InteractivePollResponse) item;
                pollIds.add(poll.getId());
                pollSizes.add(poll.getOptions().size());
                pollTypes.add(poll.isMultipleOptions());
            }
        }
        for (int i = 0; i < pollIds.size(); i++) {
            int pollSize = pollSizes.get(i);
            boolean multiple = pollTypes.get(i);
            for (int t = 3; t < 15; t++) {
                if (rnd.nextBoolean()) {
                    Set<Integer> answers = new HashSet<>();
                    if (multiple) {
                        int numberOfAnswers = rnd.nextInt(pollSize) + 1;
                        for (int j = 0; j < numberOfAnswers; j++) {
                            answers.add(rnd.nextInt(pollSize));
                        }
                    } else {
                        answers.add(rnd.nextInt(pollSize));
                    }
                    statisticsService.registerPollAnswer(pollIds.get(i), new ArrayList<>(answers), "user" + t);
                }
            }
        }
    }
}