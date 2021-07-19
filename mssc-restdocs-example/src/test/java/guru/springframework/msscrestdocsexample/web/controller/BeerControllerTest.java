package guru.springframework.msscrestdocsexample.web.controller;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.msscrestdocsexample.web.model.BeerDto;
import guru.springframework.msscrestdocsexample.web.model.BeerStyleEnum;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.guru", uriPort = 80)
@WebMvcTest
class BeerControllerTest {
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;

	@Test
	void getBeerById() throws Exception {
		mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/beer/{beerId}", UUID.randomUUID())
			.param("isCold", "true")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcRestDocumentation.document("v1/beer",
				RequestDocumentation.pathParameters(
					RequestDocumentation.parameterWithName("beerId").description("UUID of the desired beer to get.")
				),
				RequestDocumentation.requestParameters(
					RequestDocumentation.parameterWithName("isCold").description("Is beer cold query parameter.")
				),
				PayloadDocumentation.responseFields(
					PayloadDocumentation.fieldWithPath("id").description("Id of the beer"),
					PayloadDocumentation.fieldWithPath("version").description("Version number"),
					PayloadDocumentation.fieldWithPath("createdDate").description("Creation date"),
					PayloadDocumentation.fieldWithPath("lastModifiedDate").description("Update date"),
					PayloadDocumentation.fieldWithPath("beerName").description("Beer name"),
					PayloadDocumentation.fieldWithPath("style").description("Style of the beer"),
					PayloadDocumentation.fieldWithPath("upc").description("UPC of the beer"),
					PayloadDocumentation.fieldWithPath("price").description("Price"),
					PayloadDocumentation.fieldWithPath("quantityOnHand").description("Quantity on hand")
				)
			));
	}
	@Test
	void saveNewBeer() throws Exception {
		BeerDto beerDto = BeerDto.builder().upc(1L).style(BeerStyleEnum.ALE).beerName("TEST").price(new BigDecimal("1.99")).build();
		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
		
		mockMvc.perform(
				RestDocumentationRequestBuilders.post("/api/v1/beer")
				.content(objectMapper.writeValueAsString(beerDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andDo(MockMvcRestDocumentation.document("v1/beer",
				PayloadDocumentation.requestFields(
					fields.withPath("id").ignored(),
					fields.withPath("version").ignored(),
					fields.withPath("createdDate").ignored(),
					fields.withPath("lastModifiedDate").ignored(),
					fields.withPath("beerName").description("Beer name"),
					fields.withPath("style").description("Style of the beer"),
					fields.withPath("upc").description("UPC of the beer"),
					fields.withPath("price").description("Price"),
					fields.withPath("quantityOnHand").ignored()
				)
			));
	}
	@Test
	void updateBeerById() throws Exception {
		BeerDto beerDto = BeerDto.builder().upc(1L).style(BeerStyleEnum.ALE).beerName("TEST").price(new BigDecimal("1.99")).build();
		mockMvc.perform(
				RestDocumentationRequestBuilders.put("/api/v1/beer/" + UUID.randomUUID())
				.content(objectMapper.writeValueAsString(beerDto))
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	private static class ConstrainedFields {
		private final ConstraintDescriptions constraintDescriptions;
		ConstrainedFields(Class<?> input) {
			constraintDescriptions = new ConstraintDescriptions(input);
		}
		private FieldDescriptor withPath(String path) {
			return PayloadDocumentation.fieldWithPath(path)
				.attributes(
					Attributes.key("constraints")
						.value(StringUtils.collectionToDelimitedString(constraintDescriptions.descriptionsForProperty(path), ". "))
				);
		}
	}
}
