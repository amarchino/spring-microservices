package guru.springframework.msscbrewery.domain;

import java.util.UUID;

import guru.springframework.msscbrewery.web.model.v2.BeerStyleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

	private UUID id;
	private String beerName;
	private BeerStyleEnum beerStyle;
	private Long upc;
}
