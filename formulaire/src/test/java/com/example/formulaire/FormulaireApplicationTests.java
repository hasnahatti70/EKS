package com.example.formulaire;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FormulaireApplicationTests {

    @Test
    void applicationStartsCorrectly() {
        String[] args = {};
        try {
            FormulaireApplication.main(args);
            assertTrue(true); // passe si l'app démarre sans erreur
        } catch (Exception e) {
            fail("L'application ne démarre pas : " + e.getMessage());
        }
    }
}
