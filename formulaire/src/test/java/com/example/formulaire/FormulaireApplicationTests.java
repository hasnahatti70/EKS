package com.example.formulaire;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FormulaireApplicationTests {

	@Test
	void testGetAppName() {
    FormulaireApplication app = new FormulaireApplication();
    assertEquals("formulaire", app.getAppName());
}
}
