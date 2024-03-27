package com.warney.application.domain.exception;

public class ChampionNotFoundException extends RuntimeException {
    public ChampionNotFoundException(Long id) {
        super("Champion with id %d not found".formatted(id));
    }
}
