package com.thanhdatpb.guessing_game_inmobitest.controller;

import com.thanhdatpb.guessing_game_inmobitest.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/guess")
    public ResponseEntity<?> guess(
            @RequestParam Long userId,
            @RequestParam int number
    )

    {
        return ResponseEntity.ok(gameService.guess(userId, number));
    }
}
