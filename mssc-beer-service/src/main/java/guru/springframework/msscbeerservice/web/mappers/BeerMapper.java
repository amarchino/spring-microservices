package guru.springframework.msscbeerservice.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.web.model.BeerDto;

@Mapper(uses = { DateMapper.class })
public interface BeerMapper {
	
	@Mapping(target = "quantityOnHand", source = "minOnHand")
	@Mapping(target = "style", source = "beerStyle")
	BeerDto beerToBeerDto(Beer beer);
	@Mapping(target = "beerStyle", source = "style")
	@Mapping(target = "minOnHand", source = "quantityOnHand")
	@Mapping(target = "quantityToBrew", ignore = true)
	Beer beerDtoToBeer(BeerDto beerDto);

}
