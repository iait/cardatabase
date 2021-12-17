package com.example.cardatabase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.Cookie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.cardatabase.domain.AccountCredentials;
import com.example.cardatabase.domain.Car;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CarRestTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void givenCorrectUserAndPass_whenLogin_thenAuthorize() throws Exception {

		// testing authentication with correct credentials
		String rightCred = new ObjectMapper()
				.writeValueAsString(AccountCredentials.builder()
						.username("admin")
						.password("admin")
						.build());
		mvc.perform(post("/login").content(rightCred))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void givenWrongUserAndPass_whenLogin_thenUnauthorize() throws Exception {

		// testing authentication with wrong credentials
		String rightCred = new ObjectMapper()
				.writeValueAsString(AccountCredentials.builder()
						.username("admin")
						.password("wrong")
						.build());
		mvc.perform(post("/login").content(rightCred))
			.andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	public void givenMissingJwt_whenGetCars_thenForbide() throws Exception {

		mvc.perform(get("/api/cars"))
			.andDo(print())
			.andExpect(status().isForbidden());
	}

	@Test
	public void givenJwt_whenGetCars_thenOk() throws Exception {

		// authenticate with correct credentials
		String rightCred = new ObjectMapper()
				.writeValueAsString(AccountCredentials.builder()
						.username("admin")
						.password("admin")
						.build());
		MvcResult authResp = mvc.perform(post("/login").content(rightCred))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
		String jwt = authResp.getResponse().getHeader("Authorization");

		// query cars
		mvc.perform(get("/api/cars")
				.header("Authorization", jwt))
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void givenJwtAndCsrfToken_whenPostNewCar_thenCreate() throws Exception {

		// authenticate with correct credentials
		String rightCred = new ObjectMapper()
				.writeValueAsString(AccountCredentials.builder()
						.username("admin")
						.password("admin")
						.build());
		MvcResult authResp = mvc.perform(post("/login").content(rightCred))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
		String jwt = authResp.getResponse().getHeader("Authorization");
		String csrfToken = authResp.getResponse().getCookie("XSRF-TOKEN").getValue();

		// create new car
		String newCar = new ObjectMapper()
				.writeValueAsString(Car.builder()
						.brand("Chevrolet")
						.model("Agile")
						.color("White")
						.price(10000)
						.registerNumber("ABC-1122")
						.build());
		mvc.perform(post("/api/cars")
				.content(newCar)
				.cookie(new Cookie("XSRF-TOKEN", csrfToken))
				.header("X-XSRF-TOKEN", csrfToken)
				.header("Authorization", jwt))
			.andDo(print())
			.andExpect(status().isCreated());
	}

}
