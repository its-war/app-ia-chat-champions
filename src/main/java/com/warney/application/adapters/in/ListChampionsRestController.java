package com.warney.application.adapters.in;

import com.warney.application.app.ListChampionsUseCase;
import com.warney.application.domain.model.Champion;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "Campeões", description = "Endpoints de campeões do LOL.")
@RestController
@RequestMapping("/champions")
public record ListChampionsRestController(ListChampionsUseCase useCase) {

    @GetMapping
    public List<Champion> findAllChampions(){
        return useCase.findAll();
    }

    public Optional<Champion> findChampionById(Long id){
        return useCase.findById(id);
    }
}
