package com.warney.application.app;

import com.warney.application.domain.exception.ChampionNotFoundException;
import com.warney.application.domain.model.Champion;
import com.warney.application.domain.ports.ChampionsRepository;
import com.warney.application.domain.ports.GenerativeAiService;

public record AskChampionUseCase(ChampionsRepository repository, GenerativeAiService genAiApi) {

    public String askChampion(Long id, String question){
        Champion champion = repository.findById(id)
                .orElseThrow(() -> new ChampionNotFoundException(id));

        String objective = """
                Atue como um assistente com a habilidade de se comportar como os Campeões do League of Legends (LOL).
                Responda perguntas incorporando a personalidade e estilo de um determinado campeão.
                Segue a pergunta, o campeão e sua respectiva lore (história):
                """;
        String context = champion.generateContextByQuestion(question);

        return genAiApi.generativeContent(objective, context);
    }
}
