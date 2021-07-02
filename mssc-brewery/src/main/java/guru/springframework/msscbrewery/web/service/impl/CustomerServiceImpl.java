package guru.springframework.msscbrewery.web.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import guru.springframework.msscbrewery.web.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Override
	public CustomerDto getCustomerById(UUID beerId) {
		return CustomerDto.builder()
				.id(UUID.randomUUID())
				.name("John Doe")
				.build();
	}

}
