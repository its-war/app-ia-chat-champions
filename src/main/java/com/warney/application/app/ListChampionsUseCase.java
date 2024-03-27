package com.warney.application.app;

import com.warney.application.domain.model.Champion;
import com.warney.application.domain.ports.ChampionsRepository;

import java.util.List;
import java.util.Optional;

public record ListChampionsUseCase (ChampionsRepository repository) {

    public List<Champion> findAll(){
        return repository.findAll();
    }

    public Optional<Champion> findById(Long id){
        return repository.findById(id);
    }
}
