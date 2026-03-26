package com.thanhdatpb.guessing_game_inmobitest.service;

import com.thanhdatpb.guessing_game_inmobitest.entity.GuessHistory;
import com.thanhdatpb.guessing_game_inmobitest.entity.User;
import com.thanhdatpb.guessing_game_inmobitest.repository.GuessHistoryRepository;
import com.thanhdatpb.guessing_game_inmobitest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameService {

    private final UserRepository userRepository;
    private final GuessHistoryRepository historyRepository;


    @Transactional
    public Map<String, Object> guess(Long userId, int number) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getTurns() <= 0) {
            throw new RuntimeException("No turns left");
        }

        user.setTurns(user.getTurns() - 1);

        boolean win = Math.random() < 0.05;

        boolean correct;
        if (win) {
            correct = true;
            user.setScore(user.getScore() + 1);
        } else {
            correct = false;
        }

        userRepository.save(user);

        GuessHistory history = new GuessHistory();
        history.setGuessedNumber(number);
        history.setResult(correct);
        historyRepository.save(history);

        return Map.of(
                "correct", correct,
                "score", user.getScore(),
                "remainingTurns", user.getTurns()
        );
    }
}