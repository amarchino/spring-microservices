package guru.springframework.msscbrewery.web.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import guru.springframework.msscbrewery.web.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Override
	public CustomerDto getCustomerById(UUID beerId) {
		return CustomerDto.builder()
				.id(UUID.randomUUID())
				.name("John Doe")
				.build();
	}

	@Override
	public CustomerDto saveNewCustomer(CustomerDto customerDto) {
		return CustomerDto.builder()
				.id(UUID.randomUUID())
				.build();
	}

	@Override
	public void updateCustomer(UUID customerId, CustomerDto customerDto) {
		// TODO
	}

	@Override
	public void deleteById(UUID customerId) {
		log.debug("Deleting a customer with id " + customerId + "...");
	}

}
