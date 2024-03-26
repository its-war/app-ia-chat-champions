package com.warney.application.app;

import com.warney.application.domain.model.Champions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ListChampionsUseCaseIntegrationTest {

    @Autowired
    private ListChampionsUseCase useCase;

    @Test
    public void testListChampionsUseCase() {

        List<Champions> list = useCase.findAll();
        Assertions.assertEquals(12, list.size());
    }
}
