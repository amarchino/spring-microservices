package guru.springframework.msscjacksonexamples.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseTest {
	
	@Autowired ObjectMapper objectMapper;

	BeerDto getDto() {
		return BeerDto.builder()
				.beerName("Beer name")
				.beerStyle("Ale")
				.id(UUID.randomUUID())
				.createdDate(OffsetDateTime.now())
				.lastUpdatedTime(OffsetDateTime.now())
				.price(new BigDecimal("12.99"))
				.upc(123123123123L)
				.build();
	}
}
