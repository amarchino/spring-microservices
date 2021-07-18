package guru.springframework.msscrestdocsexample.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.msscrestdocsexample.web.model.BeerDto;
import guru.springframework.msscrestdocsexample.web.model.BeerStyleEnum;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
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
