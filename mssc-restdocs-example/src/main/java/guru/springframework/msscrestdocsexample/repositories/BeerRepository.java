package guru.springframework.msscrestdocsexample.repositories;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import guru.springframework.msscrestdocsexample.domain.Beer;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

}
