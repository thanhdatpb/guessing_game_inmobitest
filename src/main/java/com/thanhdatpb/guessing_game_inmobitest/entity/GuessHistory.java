package com.thanhdatpb.guessing_game_inmobitest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "guess_history")
@Getter
@Setter
public class GuessHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer guessedNumber;
    private Boolean result;
}
