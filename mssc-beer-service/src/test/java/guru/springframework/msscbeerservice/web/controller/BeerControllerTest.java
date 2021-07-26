package guru.springframework.msscbeerservice.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.msscbeerservice.bootstrap.BeerLoader;
import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;

@WebMvcTest(BeerController.class)
class BeerControllerTest {
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	@MockBean private BeerService beerService;

	@Test
	void getBeerById() throws Exception {
		BDDMockito.given(beerService.getById(ArgumentMatchers.any(UUID.class))).willReturn(getValueBeerDto());
		
		mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	@Test
	void saveNewBeer() throws Exception {
		BeerDto beerDto = getValueBeerDto();
		BDDMockito.given(beerService.saveNewBeer(ArgumentMatchers.any(BeerDto.class))).willReturn(getValueBeerDto());
		
		mockMvc.perform(
				post("/api/v1/beer")
				.content(objectMapper.writeValueAsString(beerDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isCreated());
	}
	
	@Test
	void updateBeerById() throws Exception {
		BeerDto beerDto = getValueBeerDto();
		BDDMockito.given(beerService.updateBeer(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(BeerDto.class))).willReturn(getValueBeerDto());
		mockMvc.perform(
				put("/api/v1/beer/" + UUID.randomUUID())
				.content(objectMapper.writeValueAsString(beerDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isNoContent());
	}
	
	private BeerDto getValueBeerDto() {
		return BeerDto.builder().upc(BeerLoader.BEER_1_UPC).style(BeerStyleEnum.ALE).beerName("TEST").price(new BigDecimal("1.99")).build();
	}
}
