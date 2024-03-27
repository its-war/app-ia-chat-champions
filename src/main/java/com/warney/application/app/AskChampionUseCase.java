package com.warney.application.app;

import com.warney.application.domain.exception.ChampionNotFoundException;
import com.warney.application.domain.model.Champion;
import com.warney.application.domain.ports.ChampionsRepository;

public record AskChampionUseCase(ChampionsRepository repository) {

    public String askChampion(Long id, String question){
        Champion champion = repository.findById(id)
                .orElseThrow(() -> new ChampionNotFoundException(id));

        // TODO: evoluir a lógica de negocio para considerar a integração com IAs generativas.

        return champion.generateContextByQuestion(question);
    }
}
