package guru.springframework.msscbreweryclient.web.client;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class CustomerClientTest {
	
	@Autowired CustomerClient customerClient;

	@Test
	void getCustomerById() {
		CustomerDto beerDto = customerClient.getCustomerById(UUID.randomUUID());
		assertNotNull(beerDto);
	}

	@Test
	void saveNewCustomer() {
		CustomerDto beerDto = CustomerDto.builder().name("New Customer").build();
		URI uri = customerClient.saveNewCustomer(beerDto);
		assertNotNull(uri);
		log.info(uri.toString());
	}
	
	@Test
	void updateCustomer() {
		CustomerDto beerDto = CustomerDto.builder().name("New Customer").build();
		customerClient.updateCustomer(UUID.randomUUID(), beerDto);
	}
	
	@Test
	void deleteCustomer() {
		customerClient.deleteCustomer(UUID.randomUUID());
	}
}
