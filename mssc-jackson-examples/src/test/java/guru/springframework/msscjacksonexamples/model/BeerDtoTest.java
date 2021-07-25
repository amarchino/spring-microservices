package guru.springframework.msscjacksonexamples.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@JsonTest
class BeerDtoTest extends BaseTest {
	
	@Test
	void testSerializeDto() throws JsonProcessingException {
		BeerDto beerDto = getDto();
		String jsonString = objectMapper.writeValueAsString(beerDto);
		System.out.println(jsonString);
	}
	
	@Test
	void testDeserialize() throws JsonMappingException, JsonProcessingException {
		String json = "{\"beerName\":\"Beer name\",\"beerStyle\":\"Ale\",\"upc\":123123123123,\"price\":\"12.99\",\"createdDate\":\"2021-07-25T22:16:47+0200\",\"lastUpdatedTime\":\"2021-07-25T22:16:47.645649+02:00\",\"myLocalDate\":\"20210725\",\"beerId\":\"5cf968e8-e604-43d3-b009-18d82abaef27\"}";
		BeerDto dto = objectMapper.readValue(json, BeerDto.class);
		System.out.println(dto);
	}

}
