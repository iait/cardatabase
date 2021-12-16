package com.example.cardatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.cardatabase.domain.Car;
import com.example.cardatabase.domain.CarRepository;
import com.example.cardatabase.domain.Owner;
import com.example.cardatabase.domain.OwnerRepository;
import com.example.cardatabase.domain.User;
import com.example.cardatabase.domain.UserRepository;

@SpringBootApplication
public class CardatabaseApplication {

	private static final Logger logger = LoggerFactory.getLogger(CardatabaseApplication.class);

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {

		SpringApplication.run(CardatabaseApplication.class, args);
		logger.info("Hello Spring Boot");
	}

	@Bean
	public CommandLineRunner runner() {
		return args -> {

			// Add owner objects and save these to db
			Owner john = ownerRepository.save(Owner.builder()
					.firstName("John")
					.lastName("Johnson")
					.build());
			Owner mary = ownerRepository.save(Owner.builder()
					.firstName("Mary")
					.lastName("Robinson")
					.build());

			// Add car object with link to owners and save these to db
			carRepository.save(Car.builder()
					.brand("Ford")
					.model("Mustang")
					.color("Red")
					.registerNumber("ADF-1121")
					.year(2017)
					.price(59000)
					.owner(john)
					.build());
			carRepository.save(Car.builder()
					.brand("Nissan")
					.model("Leaf")
					.color("White")
					.registerNumber("SSJ-3002")
					.year(2014)
					.price(29000)
					.owner(mary)
					.build());
			carRepository.save(Car.builder()
					.brand("Toyota")
					.model("Prius")
					.color("Silver")
					.registerNumber("KKO-0212")
					.year(2018)
					.price(39000)
					.owner(mary)
					.build());

			// username: user password: user
			userRepository.save(User.builder()
					.username("user")
					.password("$2a$04$1.YhMIgNX/8TkCKGFUONWO1waedKhQ5KrnB30fl0Q01QKqmzLf.Zi")
					.role("USER")
					.build());
			// username: admin password: admin
			userRepository.save(User.builder()
					.username("admin")
					.password("$2a$04$KNLUwOWHVQZVpXyMBNc7JOzbLiBjb9Tk9bP7KNcPI12ICuvzXQQKG")
					.role("ADMIN")
					.build());
		};
	}

}
