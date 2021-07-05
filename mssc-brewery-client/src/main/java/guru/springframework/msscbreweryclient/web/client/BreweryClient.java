package guru.springframework.msscbreweryclient.web.client;

import java.net.URI;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import guru.springframework.msscbreweryclient.web.model.BeerDto;
import lombok.Data;

@Component
@ConfigurationProperties(value = "sfg.brewery",ignoreUnknownFields = true)
@Data
public class BreweryClient {

	public static final String BEER_PATH_V1 = "/api/v1/beer";
	private String apihost;
	private final RestTemplate restTemplate;
	
	public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}
	
	public BeerDto getBeerById(UUID id) {
		return restTemplate.getForObject(apihost + BEER_PATH_V1 + "/" + id.toString(), BeerDto.class);
	}
	
	public URI saveNewBeer(BeerDto beerDto) {
		return restTemplate.postForLocation(apihost + BEER_PATH_V1, beerDto);
	}
	
	public void updateBeer(UUID id, BeerDto beerDto) {
		restTemplate.put(apihost + BEER_PATH_V1 + "/" + id.toString(), beerDto);
	}
	
	public void deleteBeer(UUID id) {
		restTemplate.delete(apihost + BEER_PATH_V1 + "/" + id.toString());
	}
}
