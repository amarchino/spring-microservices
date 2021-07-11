package guru.springframework.msscbrewery.web.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import guru.springframework.msscbrewery.web.service.CustomerService;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerService customerService;
	
	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") UUID customerId) {
		return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> handlePost(@Valid @NotNull @RequestBody CustomerDto customerDto) {
		CustomerDto savedCustomer = customerService.saveNewCustomer(customerDto);
		HttpHeaders headers = new HttpHeaders();
		// TODO: add hostname to URL
		headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/{customerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void handlePut(@PathVariable("customerId") UUID customerId, @Valid @NotNull @RequestBody CustomerDto customerDto) {
		customerService.updateCustomer(customerId, customerDto);
	}
	
	@DeleteMapping("/{customerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCustomer(@PathVariable("customerId") UUID customerId) {
		customerService.deleteById(customerId);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<String>> validationErrorHandler(MethodArgumentNotValidException e) {
		List<String> errors = e.getBindingResult()
				.getAllErrors()
				.stream()
				.map(FieldError.class::cast)
				.map(fieldError -> String.format("Bad Request %s: %s. Rejected value: ---> %s", fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue()))
				.collect(Collectors.toList());
		return ResponseEntity.badRequest().body(errors);
	}
}
