package org.example.controller;

import org.example.model.Checkout;
import org.example.model.RentalAgreement;
import org.example.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})

@RestController
public class StoreController {

	private final CheckoutService checkoutService;

	@Autowired
	public StoreController(CheckoutService checkoutService) {
		this.checkoutService = checkoutService;
	}

	@PostMapping(value = "/checkout",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public RentalAgreement checkout(@Valid @RequestBody Checkout checkout) {
		RentalAgreement rentalAgreement = checkoutService.getRentalAgreement(checkout);
		rentalAgreement.toConsole();
		return rentalAgreement;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
			MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}