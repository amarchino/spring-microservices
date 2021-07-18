package guru.springframework.msscrestdocsexample.web.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
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
		mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID())
			.param("isCold", "true")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("v1/beer",
				pathParameters(
					parameterWithName("beerId").description("UUID of the desired beer to get.")
				),
				requestParameters(
					parameterWithName("isCold").description("Is beer cold query parameter.")
				),
				responseFields(
					fieldWithPath("id").description("Id of the beer"),
					fieldWithPath("version").description("Version number"),
					fieldWithPath("createdDate").description("Creation date"),
					fieldWithPath("lastModifiedDate").description("Update date"),
					fieldWithPath("beerName").description("Beer name"),
					fieldWithPath("style").description("Style of the beer"),
					fieldWithPath("upc").description("UPC of the beer"),
					fieldWithPath("price").description("Price"),
					fieldWithPath("quantityOnHand").description("Quantity on hand")
				)
			));
	}
	@Test
	void saveNewBeer() throws Exception {
		BeerDto beerDto = BeerDto.builder().upc(1L).style(BeerStyleEnum.ALE).beerName("TEST").price(new BigDecimal("1.99")).build();
		
		mockMvc.perform(
				post("/api/v1/beer")
				.content(objectMapper.writeValueAsString(beerDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isCreated())
			.andDo(document("v1/beer",
				requestFields(
					fieldWithPath("id").ignored(),
					fieldWithPath("version").ignored(),
					fieldWithPath("createdDate").ignored(),
					fieldWithPath("lastModifiedDate").ignored(),
					fieldWithPath("beerName").description("Beer name"),
					fieldWithPath("style").description("Style of the beer"),
					fieldWithPath("upc").description("UPC of the beer"),
					fieldWithPath("price").description("Price"),
					fieldWithPath("quantityOnHand").ignored()
				)
			));
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
