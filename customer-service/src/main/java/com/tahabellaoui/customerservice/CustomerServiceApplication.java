package com.tahabellaoui.customerservice;

import com.tahabellaoui.customerservice.entities.Customer;
import com.tahabellaoui.customerservice.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	// Ce bloc de code s'exécute automatiquement au démarrage de l'application
	@Bean
	CommandLineRunner commandLineRunner(CustomerService customerService) {
		return args -> {
			// 1. On crée une liste de noms
			List<String> names = List.of("Mohamed", "Imane", "yassine");

			// 2. Pour chaque nom, on crée un client et on le sauvegarde en base
			names.forEach(name -> {
				customerService.saveCustomer(Customer.builder()
						.name(name)
						.email(name + "@gmail.com")
						.build());
			});
		};
	}
}