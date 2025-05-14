package com.example.formulaire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.formulaire.repository")
@EntityScan(basePackages = "com.example.formulaire.model")
public class FormulaireApplication {
       public String getAppName() {
             return "formulaire";
}

	public static void main(String[] args) {
		SpringApplication.run(FormulaireApplication.class, args);
	}

}
