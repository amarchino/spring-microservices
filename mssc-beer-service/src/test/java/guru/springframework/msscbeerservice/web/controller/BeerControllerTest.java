package guru.springframework.msscbeerservice.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;

@WebMvcTest
class BeerControllerTest {
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@Test
	void getBeerById() throws Exception {
		mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	@Test
	void saveNewBeer() throws Exception {
		BeerDto beerDto = BeerDto.builder().upc(1L).style(BeerStyleEnum.ALE).beerName("TEST").price(new BigDecimal("1.99")).build();
		
		mockMvc.perform(
				post("/api/v1/beer")
				.content(objectMapper.writeValueAsString(beerDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isCreated());
	}
	@Test
	void updateBeerById() throws Exception {
		BeerDto beerDto = BeerDto.builder().upc(1L).style(BeerStyleEnum.ALE).beerName("TEST").price(new BigDecimal("1.99")).build();
		mockMvc.perform(
				put("/api/v1/beer/" + UUID.randomUUID())
				.content(objectMapper.writeValueAsString(beerDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isNoContent());
	}
}
