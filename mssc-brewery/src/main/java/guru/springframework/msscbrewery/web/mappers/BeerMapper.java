package guru.springframework.msscbrewery.web.mappers;

import org.mapstruct.Mapper;

import guru.springframework.msscbrewery.domain.Beer;
import guru.springframework.msscbrewery.web.model.BeerDto;
import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;

@Mapper
public interface BeerMapper {

	BeerDtoV2 beerToBeerDtoV2(Beer beer);
	Beer beerDtoV2ToBeer(BeerDtoV2 beer);
	
	BeerDto beerToBeerDto(Beer beer);
	Beer beerDtoToBeer(BeerDto beer);
}
