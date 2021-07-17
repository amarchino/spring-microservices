package guru.springframework.msscrestdocsexample.web.controller.advice;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MvcExceptionHandler {

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<String> methodArgumentNotValidHandler(BindException e) {
		return e.getBindingResult()
				.getAllErrors()
				.stream()
				.map(FieldError.class::cast)
				.map(fieldError -> String.format("Bad Request %s: %s. Rejected value: ---> %s", fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue()))
				.collect(Collectors.toList());
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public List<String> constraintViolationHandler(ConstraintViolationException e) {
		return e.getConstraintViolations()
				.stream()
				.map(cv -> String.format("Bad Request %s: %s. Rejected value: ---> %s", cv.getPropertyPath(), cv.getMessage(), cv.getInvalidValue()))
				.collect(Collectors.toList());
	}
}
