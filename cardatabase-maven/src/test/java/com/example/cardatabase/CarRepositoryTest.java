package com.example.cardatabase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.cardatabase.domain.Car;
import com.example.cardatabase.domain.CarRepository;

@DataJpaTest
public class CarRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CarRepository carRepository;

	@Test
	public void saveCar() {

		Car car = Car.builder()
				.brand("Tesla")
				.model("Model X")
				.color("white")
				.registerNumber("ABC-1234")
				.year(2017)
				.price(86000)
				.build();
		entityManager.persistAndFlush(car);

		System.out.println(car.getId());
		assertThat(car.getId()).isNotNull();
	}

	@Test
	public void deleteCars() {

		entityManager.persistAndFlush(Car.builder()
				.brand("Tesla")
				.model("Model X")
				.color("white")
				.registerNumber("ABC-1234")
				.year(2017)
				.price(86000)
				.build());
		entityManager.persistAndFlush(Car.builder()
				.brand("Mini")
				.model("Cooper")
				.color("Yellow")
				.registerNumber("BWS-3007")
				.year(2015)
				.price(24500)
				.build());

		assertThat(carRepository.findAll()).isNotEmpty();
		carRepository.deleteAll();
		assertThat(carRepository.findAll()).isEmpty();
	}

}
