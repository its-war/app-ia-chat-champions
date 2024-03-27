package com.warney.application.adapters.in;

import com.warney.application.app.AskChampionUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Campeões", description = "Endpoints de campeões do LOL.")
@RestController
@RequestMapping("/champions")
public record AskChampionRestController(AskChampionUseCase useCase) {

    @PostMapping("/{id}/ask")
    public AskChampionResponse askChampion(@PathVariable Long id, @RequestBody AskChampionRequest request){
        String asnwer = useCase.askChampion(id, request.question());
        return new AskChampionResponse(asnwer);
    }

    public record AskChampionRequest(String question) {}
    public record AskChampionResponse(String answer) {}
}
