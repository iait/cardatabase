package com.example.cardatabase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.cardatabase.web.CarController;

@SpringBootTest
class CardatabaseApplicationTests {

	@Autowired
	private CarController carController;

	@Test
	void contextLoads() {

		assertThat(carController).isNotNull();
	}

}
