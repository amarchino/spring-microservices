package guru.springframework.msscbrewery.web.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.UUID;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
public class BeerControllerTest {

	@MockBean
	BeerService beerService;

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	BeerDto validBeer;

	@BeforeEach
	public void setUp() {
		validBeer = BeerDto.builder().id(UUID.randomUUID()).beerName("Beer1").beerStyle("PALE_ALE").upc(123456789012L)
				.build();
	}

	@Test
	public void getBeer() throws Exception {
		BDDMockito.given(beerService.getBeerById(ArgumentMatchers.any(UUID.class))).willReturn(validBeer);
		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

		mockMvc.perform(
				RestDocumentationRequestBuilders.get("/api/v1/beer/{beerId}", validBeer.getId().toString()
			)
			.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", Is.is(validBeer.getId().toString())))
			.andExpect(jsonPath("$.beerName", Is.is("Beer1")))
			.andDo(MockMvcRestDocumentation.document("v1/beer-get",
				RequestDocumentation.pathParameters(
					RequestDocumentation.parameterWithName("beerId").description("UUID of the desired beer to get.")
				),
				PayloadDocumentation.responseFields(
					fields.withPath("id").description("Id of the beer"),
					fields.withPath("createdDate").description("Creation date"),
					fields.withPath("lastUpdatedDate").description("Update date"),
					fields.withPath("beerName").description("Beer name"),
					fields.withPath("beerStyle").description("Style of the beer"),
					fields.withPath("upc").description("UPC of the beer")
				)
			));
	}

	@Test
	public void handlePost() throws Exception {
		// given
		BeerDto beerDto = validBeer;
		beerDto.setId(null);
		BeerDto savedDto = BeerDto.builder().id(UUID.randomUUID()).beerName("New Beer").build();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

		BDDMockito.given(beerService.saveNewBeer(ArgumentMatchers.any())).willReturn(savedDto);

		mockMvc.perform(
			RestDocumentationRequestBuilders.post("/api/v1/beer/")
			.contentType(MediaType.APPLICATION_JSON)
			.content(beerDtoJson)
		)
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andDo(MockMvcRestDocumentation.document("v1/beer-new",
			PayloadDocumentation.requestFields(
				fields.withPath("id").ignored(),
				fields.withPath("createdDate").ignored(),
				fields.withPath("lastUpdatedDate").ignored(),
				fields.withPath("beerName").description("Beer name"),
				fields.withPath("beerStyle").description("Style of the beer"),
				fields.withPath("upc").description("UPC of the beer")
			)
		));
	}

	@Test
	public void handleUpdate() throws Exception {
		// given
		BeerDto beerDto = validBeer;
		beerDto.setId(null);
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

		// when
		mockMvc.perform(
			RestDocumentationRequestBuilders.put("/api/v1/beer/{beerId}", UUID.randomUUID())
			.contentType(MediaType.APPLICATION_JSON)
			.content(beerDtoJson)
		)
		.andExpect(MockMvcResultMatchers.status().isNoContent())
		.andDo(MockMvcRestDocumentation.document("v1/beer-update",
			RequestDocumentation.pathParameters(
				RequestDocumentation.parameterWithName("beerId").description("UUID of the desired beer to update.")
			),
			PayloadDocumentation.requestFields(
					fields.withPath("id").ignored(),
					fields.withPath("createdDate").ignored(),
					fields.withPath("lastUpdatedDate").ignored(),
					fields.withPath("beerName").description("Beer name"),
					fields.withPath("beerStyle").description("Style of the beer"),
					fields.withPath("upc").description("UPC of the beer")
				)
			));

		BDDMockito.then(beerService).should().updateBeer(ArgumentMatchers.any(), ArgumentMatchers.any());

	}

	private static class ConstrainedFields {
		private final ConstraintDescriptions constraintDescriptions;

		ConstrainedFields(Class<?> input) {
			constraintDescriptions = new ConstraintDescriptions(input);
		}

		private FieldDescriptor withPath(String path) {
			return PayloadDocumentation.fieldWithPath(path).attributes(Attributes.key("constraints").value(StringUtils
					.collectionToDelimitedString(constraintDescriptions.descriptionsForProperty(path), ". ")));
		}
	}
}