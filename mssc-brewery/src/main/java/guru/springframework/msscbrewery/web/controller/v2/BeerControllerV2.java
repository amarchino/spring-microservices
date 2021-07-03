package guru.springframework.msscbrewery.web.controller.v2;

import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.msscbrewery.web.model.v2.BeerDtoV2;
import guru.springframework.msscbrewery.web.service.v2.BeerServiceV2;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v2/beer")
@RequiredArgsConstructor
public class BeerControllerV2 {
	
	private final BeerServiceV2 beerServiceV2;
	
	@GetMapping("/{beerId}")
	public ResponseEntity<BeerDtoV2> getBeer(@PathVariable("beerId") UUID beerId) {
		return new ResponseEntity<>(beerServiceV2.getBeerById(beerId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> handlePost(@RequestBody BeerDtoV2 beerDto) {
		BeerDtoV2 savedBeer = beerServiceV2.saveNewBeer(beerDto);
		HttpHeaders headers = new HttpHeaders();
		// TODO: add hostname to URL
		headers.add("Location", "/api/v2/beer/" + savedBeer.getId().toString());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/{beerId}")
	public ResponseEntity<Object> handlePut(@PathVariable("beerId") UUID beerId, @RequestBody BeerDtoV2 beerDto) {
		beerServiceV2.updateBeer(beerId, beerDto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{beerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBeer(@PathVariable("beerId") UUID beerId) {
		beerServiceV2.deleteById(beerId);
	}
}
