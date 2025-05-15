package com.example.formulaire;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FormulaireControllerTest {

    @Autowired
    private FormulaireController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }
}
