package com.belkin.yahack.serivce;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.belkin.yahack.api.dto.response.InteractivePollAnswerResponse;
import com.belkin.yahack.dao.InteractiveItemDAO;
import com.belkin.yahack.dao.StatsDAO;
import com.belkin.yahack.exception.not_found.ItemNotFoundException;
import com.belkin.yahack.model.InteractiveItem;
import com.belkin.yahack.model.InteractivePoll;
import com.belkin.yahack.model.stats.ButtonClick;
import com.belkin.yahack.model.stats.EpisodePlay;
import com.belkin.yahack.model.stats.PollAnswers;
import com.belkin.yahack.model.stats.StatisticRecord;
import lombok.RequiredArgsConstructor;
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
            int numberOfAnswers = pollAnswers.size();
            InteractivePoll poll = (InteractivePoll) item;
            int[] optionsCounter = new int[poll.getOptions().size()];
            pollAnswers.forEach(a -> a.getAnswers().forEach(o -> optionsCounter[o]++));
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
}
