package com.example.formulaire;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FormulaireApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testAddition() {
		FormulaireApplication app = new FormulaireApplication();
		int result = app.addition(3, 2);
		assertEquals(5, result);
	}
}
