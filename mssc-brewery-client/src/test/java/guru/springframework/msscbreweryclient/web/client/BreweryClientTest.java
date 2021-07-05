package guru.springframework.msscbreweryclient.web.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.msscbreweryclient.web.model.BeerDto;

@SpringBootTest
class BreweryClientTest {
	
	@Autowired BreweryClient breweryClient;

	@Test
	void getBeerById() {
		BeerDto beerDto = breweryClient.getBeerById(UUID.randomUUID());
		assertNotNull(beerDto);
	}

}
