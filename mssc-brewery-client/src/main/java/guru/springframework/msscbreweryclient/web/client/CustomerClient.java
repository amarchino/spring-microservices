package guru.springframework.msscbreweryclient.web.client;

import java.net.URI;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.springframework.msscbreweryclient.web.model.CustomerDto;
import lombok.Data;

@Component
@ConfigurationProperties(value = "sfg.brewery",ignoreUnknownFields = true)
@Data
public class CustomerClient {

	public static final String CUSTOMER_PATH_V1 = "/api/v1/customer";
	private String apihost;
	private final RestTemplate restTemplate;
	
	public CustomerClient(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}
	
	public CustomerDto getCustomerById(UUID id) {
		return restTemplate.getForObject(apihost + CUSTOMER_PATH_V1 + "/" + id.toString(), CustomerDto.class);
	}
	
	public URI saveNewCustomer(CustomerDto beerDto) {
		return restTemplate.postForLocation(apihost + CUSTOMER_PATH_V1, beerDto);
	}
	
	public void updateCustomer(UUID id, CustomerDto beerDto) {
		restTemplate.put(apihost + CUSTOMER_PATH_V1 + "/" + id.toString(), beerDto);
	}
	
	public void deleteCustomer(UUID id) {
		restTemplate.delete(apihost + CUSTOMER_PATH_V1 + "/" + id.toString());
	}
}
