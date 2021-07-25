package guru.springframework.msscjacksonexamples.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonTest
class BeerDtoTest extends BaseTest {
	
	@Autowired ObjectMapper objectMapper;

	@Test
	void testSerializeDto() throws JsonProcessingException {
		BeerDto beerDto = getDto();
		String jsonString = objectMapper.writeValueAsString(beerDto);
		System.out.println(jsonString);
	}
	
	@Test
	void testDeserialize() throws JsonMappingException, JsonProcessingException {
		String json = "{\"id\":\"f7f1c925-884e-48a1-9ffd-4ab4e1eb51a4\",\"beerName\":\"Beer name\",\"beerStyle\":\"Ale\",\"upc\":123123123123,\"price\":12.99,\"createdDate\":\"2021-07-25T21:42:21.045528+02:00\",\"lastUpdatedTime\":\"2021-07-25T21:42:21.046552+02:00\"}";
		BeerDto dto = objectMapper.readValue(json, BeerDto.class);
		System.out.println(dto);
	}

}
