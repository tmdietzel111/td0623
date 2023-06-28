package org.example.controller;

import org.example.exception.CheckoutException;
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

/**
 * Basic SpringBoot Controller - only has the one endpoint for checkout to handle the majority of the functionality that we need
 * <p>
 * TODO add Swagger annotations to generate Swagger file for free documentation
 */
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@RestController
public class StoreController {

	private final CheckoutService checkoutService;

	@Autowired
	public StoreController(CheckoutService checkoutService) {
		this.checkoutService = checkoutService;
	}

	/***
	 * Main method - expects a fully populated Checkout object
	 *
	 * @param checkout
	 * @return The Rental Agreement - or an error code if there's a problem
	 */
	@PostMapping(value = "/checkout",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public RentalAgreement checkout(@Valid @RequestBody Checkout checkout) {
		RentalAgreement rentalAgreement = checkoutService.getRentalAgreement(checkout);
		rentalAgreement.toConsole();
		return rentalAgreement;
	}

	/**
	 * Handles automatic mapping of errors to a more readable 400 for the @Valid annotation above
	 *
	 * @param ex
	 * @return
	 */
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

	/**
	 * Handles automatic mapping of errors to a more readable 400 for any surprise CheckoutExceptions
	 *
	 * @param ex
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(CheckoutException.class)
	public String handleCheckoutExceptions(
			CheckoutException ex) {

		return ex.getMessage();
	}
}