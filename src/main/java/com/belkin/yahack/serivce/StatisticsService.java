package com.belkin.yahack.serivce;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.belkin.yahack.api.dto.response.InteractivePollAnswerResponse;
import com.belkin.yahack.dao.InteractiveItemDAO;
import com.belkin.yahack.dao.StatsDAO;
import com.belkin.yahack.exception.RestException;
import com.belkin.yahack.exception.not_found.ItemNotFoundException;
import com.belkin.yahack.model.InteractiveItem;
import com.belkin.yahack.model.InteractivePoll;
import com.belkin.yahack.model.InteractiveText;
import com.belkin.yahack.model.stats.ButtonClick;
import com.belkin.yahack.model.stats.EpisodePlay;
import com.belkin.yahack.model.stats.FormAnswer;
import com.belkin.yahack.model.stats.PollAnswers;
import com.belkin.yahack.model.stats.StatisticRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final StatsDAO statsDAO;
    private final InteractiveItemDAO itemDAO;



    private Long registerStatsRecord(StatisticRecord record) {
        Optional<StatisticRecord> maybeRecord = statsDAO.findByElementIdAndUsername(record.getElementId(), record.getUsername());
        if (maybeRecord.isPresent())
            return maybeRecord.get().getId();
        statsDAO.save(record);
        return record.getId();
    }

    public void registerPollAnswer(String pollId, List<Integer> answers, String username) {
        InteractivePoll poll = (InteractivePoll) itemDAO.findById(pollId).orElseThrow(() -> new ItemNotFoundException(pollId));
        if (answers == null || answers.size() == 0)
            throw new RestException(HttpStatus.BAD_REQUEST, "Answers can not be empty or null");
        if (!poll.isMultipleOptions() && answers.size() != 1)
            throw new RestException(HttpStatus.BAD_REQUEST, "Single-answer poll must have single answer");
        if (answers.size() > poll.getOptions().size())
            throw new RestException(HttpStatus.BAD_REQUEST, "Too many answers provided");
        answers.forEach((Integer a) -> {
            if (a >= poll.getOptions().size() || a < 0) {
                throw new RestException(HttpStatus.BAD_REQUEST, "Answer should be in range [0, len(options)-1]");
            }
        });

        registerStatsRecord(new PollAnswers(pollId, username, answers));
    }

    public void incrementButtonClicks(String buttonId, String username) {
        registerStatsRecord(new ButtonClick(buttonId, username));
    }

    public void incrementEpisodePlays(String episodeId, String username) {
        registerStatsRecord(new EpisodePlay(episodeId, username));
    }

    public InteractivePollAnswerResponse getPollResults(String pollId) {
        List<PollAnswers> pollAnswers = statsDAO.findAllByElementId(pollId)
                .stream().map(r -> (PollAnswers)r).collect(Collectors.toList());

        InteractiveItem item = itemDAO.findById(pollId).orElseThrow(() -> new ItemNotFoundException(pollId));
        if (item instanceof InteractivePoll) {
            int numberOfAnswers = 0;
            InteractivePoll poll = (InteractivePoll) item;
            int[] optionsCounter = new int[poll.getOptions().size()];
            pollAnswers.forEach(a -> a.getAnswers().forEach(o -> optionsCounter[o]++));
            for (int i = 0; i < optionsCounter.length; i++) {
                numberOfAnswers += optionsCounter[i];
            }
            for (int i = 0; i < optionsCounter.length; i++) {
                optionsCounter[i] = (int) (optionsCounter[i] * 100. / numberOfAnswers);
            }
            List<Integer> stats = Arrays.stream(optionsCounter).boxed().collect(Collectors.toList());
            stats.remove(stats.size() - 1);
            stats.add(100 - stats.stream().reduce(Integer::sum).orElse(0));
            return new InteractivePollAnswerResponse(poll, stats);
        }
        throw new ItemNotFoundException(pollId);
    }

    public boolean existsByElementIdAndUsername(String id, String listenerUsername) {
        return statsDAO.existsByElementIdAndUsername(id, listenerUsername);
    }

    public void registerFormAnswer(String formId, String answer, String username) {
        InteractiveItem item = itemDAO.findById(formId).orElseThrow(() -> new ItemNotFoundException(formId));
        if (item instanceof InteractiveText) {
            InteractiveText text = (InteractiveText) item;
            if (text.isHasInputForm())
                registerStatsRecord(new FormAnswer(formId, username, answer));
            else
                throw new ItemNotFoundException(formId);
        }
    }
}
