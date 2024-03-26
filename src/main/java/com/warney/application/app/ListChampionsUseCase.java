package com.warney.application.app;

import com.warney.application.domain.model.Champions;
import com.warney.application.domain.ports.ChampionsRepository;

import java.util.List;
import java.util.Optional;

public record ListChampionsUseCase (ChampionsRepository repository) {

    public List<Champions> findAll(){
        return repository.findAll();
    }

    public Optional<Champions> findById(Long id){
        return repository.findById(id);
    }
}
