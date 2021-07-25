package guru.springframework.msscjacksonexamples.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BaseTest {

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
